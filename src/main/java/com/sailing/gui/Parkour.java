package com.sailing.gui;

import com.sailing.Sailboat;
import com.sailing.Simulation;
import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import com.sailing.math.solver.RungeKutta;
import com.sailing.math.solver.Solver;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.*;

/**
 *
 */
class Parkour extends Pane {

    private final SailboatGUI sailboat;

    private final Simulation simulation;

    private final Arrows arrows;

    private final Text timeText = new Text("Time: 00:00");

    double windVelocity = 8;

    // for dragging
    private double startX;

    long currentTime;

    Parkour(List<Vector2D> targetPositions) {
        sailboat = new SailboatGUI(new Sailboat(), new SailboatGUI.SailGUI(), 0.5);
        setBackground(new Background(new BackgroundImage(Images.background, null, null, null, null)));
        arrows = new Arrows(sailboat);

        // styling
        getStylesheets().add("/styling.css");
        setWidth(Sailing.WIDTH);
        setHeight(Sailing.HEIGHT);

        getChildren().addAll(sailboat);

        Vector position = new Vector(50, 25, -180, 0);
        Vector velocity = new Vector(1, 1, 0, 0).normalize().multiplyByScalar(windVelocity);
        Vector acceleration = new Vector(0, 0);
        double mass = 100;
        double time = 0;
        StateSystem stateSystem = new StateSystem(position, velocity, acceleration, mass, time);
        Solver solver = new RungeKutta();

        simulation = new Simulation(solver, stateSystem, 0.01);

        Stats windStats = new Stats(new Vector2D(0, 0), "right", "wind", stateSystem, sailboat);
        makeXDraggable(windStats, new double[]{-260, 0});

        final boolean[] running = {true};

        timeText.setX(650);
        timeText.setY(30);
        timeText.setStyle("-fx-font-size: 40px;");

        getChildren().addAll(windStats);
        getChildren().add(timeText);

        currentTime = 0;
        ArrayList<Target> targets = new ArrayList<>();
        for (int i = 0; i < targetPositions.size(); i++) {
            targets.add(new Target(i + 1, targetPositions.get(i)));
        }
        getChildren().addAll(targets);

        AnimationTimer timer = new AnimationTimer() {
            long last = 0;
            long count = 0;

            long lastTime = System.currentTimeMillis();

            @Override
            public void handle(long now) {
                // execute 10 times per second
                if (now - last >= 10_000_000) {
                    if (running[0]) {
                        simulation.run(1);
                        arrows.update(simulation.getCurrentState());
                        sailboat.repaintSail(simulation.getCurrentState());
                        windStats.update(simulation.getCurrentState());
                        count++;
                        if (count % 100 == 0) {
                            simulation.getCurrentState().log();
                        }
                        sailboat.setPosition(new Vector2D(simulation.getCurrentState().getPosition().getValue(0), simulation.getCurrentState().getPosition().getValue(1)));
                        targets.forEach(target -> target.checkHit(simulation.getCurrentState().getBoatPosition()));
                        int numberHits = targets.stream().mapToInt(t -> t.isHit() ? 1:0).sum();
                        if (numberHits < targets.size()) targets.get(numberHits).setOpen(true);
                        else {
                            running[0] = false;
                            System.out.println("You won!");
                            double seconds = currentTime / 1000.0;
                            System.out.printf("Time: %02d:%02d%n", (int)(seconds / 60), (int) (seconds % 60));
                            targets.forEach(t -> {
                                t.setOpen(false);
                                t.setHit(false);
                            });
                            getChildren().add(new WinningScreen(
                                    "Congratulation! \n Time: %02d:%02d%n".formatted((int)(seconds / 60), (int) (seconds % 60)),
                                    targetPositions));
                        }
                    }
                    last = now;
                }
                if (running[0]) currentTime += System.currentTimeMillis() - lastTime;
                lastTime = System.currentTimeMillis();
                double seconds = currentTime / 1000.0;
                timeText.setText("Time: %02d:%02d".formatted((int)(seconds / 60), (int) (seconds % 60)));
            }
        };
        timer.start();


        getChildren().add(arrows);
        arrows.update(simulation.getCurrentState());

        sailboat.rotate(simulation.getCurrentState().getPosition().getValue(2));
        sailboat.getSail().rotate(simulation.getCurrentState().getPosition().getValue(3));

        final List<KeyCode> validKeys = Arrays.asList(KeyCode.UP, KeyCode.DOWN,
                KeyCode.LEFT, KeyCode.RIGHT, KeyCode.SPACE);
        final Set<KeyCode> pressedKeys = new HashSet<>();
        setOnKeyReleased(e -> pressedKeys.clear());

        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                System.out.println("Escape");
                running[0] = false;
                getChildren().add(new EscMenu(getChildren()));
            }
            if (validKeys.contains(e.getCode())) {
                pressedKeys.add(e.getCode());
                if (pressedKeys.contains(KeyCode.UP)) {
                    simulation.rotateSail(-1);
                    sailboat.getSail().rotate(simulation.getCurrentState().getPosition().getValue(3));
                } if (pressedKeys.contains(KeyCode.DOWN)) {
                    simulation.rotateSail(1);
                    sailboat.getSail().rotate(simulation.getCurrentState().getPosition().getValue(3));
                }  if (pressedKeys.contains(KeyCode.LEFT)) {
                    simulation.rotateBoat(-1);
                    sailboat.rotate(simulation.getCurrentState().getPosition().getValue(2));
                }  if (pressedKeys.contains(KeyCode.RIGHT)) {
                    simulation.rotateBoat(1);
                    sailboat.rotate(simulation.getCurrentState().getPosition().getValue(2));
                } if (pressedKeys.contains(KeyCode.SPACE)) {
                    running[0] = !running[0];

                }
            }
            sailboat.repaintSail(simulation.getCurrentState());
            arrows.update(simulation.getCurrentState());
        });
    }


    void makeXDraggable(Node node, double[] xBounds) {
        node.setOnMousePressed(e -> startX = e.getSceneX() - node.getTranslateX());
        node.setOnMouseDragged(e -> {
            node.setTranslateX(e.getSceneX() - startX);
                if (xBounds != null) {
                    if (node.getTranslateX() > xBounds[1]) node.setTranslateX(xBounds[1]);
                    if (node.getTranslateX() < xBounds[0]) node.setTranslateX(xBounds[0]);
                }
        });
    }
}
