package com.sailing.gui;

import com.sailing.Simulation;
import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import com.sailing.math.solver.RungeKutta;
import com.sailing.math.solver.Solver;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

/**
 *
 */
class WindTunnel extends Pane {

    private final SailboatGUI sailboat;

    private final Simulation simulation;

    private final Arrows arrows;

    double windVelocity = 10;

    // for dragging
    private double startX;
    private double startY;

    WindTunnel(SailboatGUI boat) {
        this.sailboat = boat;
        setBackground(new Background(new BackgroundImage(Images.background, null, null, null, null)));
        arrows = new Arrows(boat);

        // styling
        getStylesheets().add("/styling.css");
        setWidth(Sailing.WIDTH);
        setHeight(Sailing.HEIGHT);

        getChildren().addAll(sailboat);
        Legend legend = new Legend(Legend.Force.LIFT,
                Legend.Force.DRAG,
                Legend.Force.AERO,
                Legend.Force.ACCEL,
                Legend.Force.VEL_AW,
                Legend.Force.VEL_TW);
        legend.setLayoutX(50);
        int legendScale = 60;
        legend.setLayoutY(Sailing.HEIGHT - legend.getChildren().size() * legendScale + 50);
        makeYDraggable(legend);
        getChildren().add(legend);


        Vector position = new Vector(0, 0, -90, 0);
        Vector velocity = new Vector(0, 1, 0, 0).normalize().multiplyByScalar(windVelocity);
        Vector acceleration = new Vector(0, 0);
        double mass = 100;
        double time = 0;
        StateSystem stateSystem = new StateSystem(position, velocity, acceleration, mass, time);
        Solver solver = new RungeKutta();

        simulation = new Simulation(solver, stateSystem, 0.01);

        Stats stats = new Stats();
        stats.setTranslateX(10);
        stats.setTranslateY(500);

        final boolean[] running = {true};

        AnimationTimer timer = new AnimationTimer() {
            long last = 0;
            long count = 0;

            @Override
            public void handle(long now) {
                // execute 10 times per second
                if (now - last >= 10_000_000) {
                    if (running[0]) {
                        simulation.run(1);
                        arrows.update(simulation.getCurrentState());
                        boat.repaintSail(simulation.getCurrentState());
                        stats.update(simulation.getCurrentState());
                        count++;
                        if (count % 100 == 0) {
                            simulation.getCurrentState().log();
                        }
                        boat.setPosition(new Vector2D(simulation.getCurrentState().getPosition().getValue(0), simulation.getCurrentState().getPosition().getValue(1)));
                    }
                    last = now;
                }
            }
        };

        timer.start();


        getChildren().addAll(
                stats,
                arrows);
        arrows.update(simulation.getCurrentState());

//        Text windLabel = new Text("Wind");
//        windLabel.setFill(Color.ALICEBLUE);
//        windLabel.setX(80);
//        windLabel.setY(90);
//        getChildren().add(windLabel);
        sailboat.rotate(simulation.getCurrentState().getPosition().getValue(2));
        sailboat.getSail().rotate(simulation.getCurrentState().getPosition().getValue(3));

        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT -> {
                    simulation.rotateBoat(-1);
                    sailboat.rotate(simulation.getCurrentState().getPosition().getValue(2));
                }
                case RIGHT -> {
                    simulation.rotateBoat(1);
                    sailboat.rotate(simulation.getCurrentState().getPosition().getValue(2));
                }
                case UP -> {
                    simulation.rotateSail(-1);
                    sailboat.getSail().rotate(simulation.getCurrentState().getPosition().getValue(3));
                }
                case DOWN -> {
                    simulation.rotateSail(1);
                    sailboat.getSail().rotate(simulation.getCurrentState().getPosition().getValue(3));
                }
                case SPACE -> {
                    running[0] = !running[0]; // pause
                    simulation.getCurrentState().log();
                }
           }
           boat.repaintSail(simulation.getCurrentState());
           arrows.update(simulation.getCurrentState());
       });

       Scale s = new Scale();
       s.setPivotX(Sailing.X_CENTER);
       s.setPivotY(Sailing.Y_CENTER);
       sailboat.getTransforms().add(s);

       setOnScroll(event -> {
           s.setX(s.getX() + event.getDeltaY() / 500);
           s.setY(s.getY() + event.getDeltaY() / 500);
       });
    }

    public SailboatGUI getSailboat() {
        return sailboat;
    }

    void makeYDraggable(Node node) {
        node.setOnMouseEntered(e -> {
            startY = e.getSceneY() - node.getTranslateY();
        });
        node.setOnMouseDragged(e -> {
            node.setTranslateY(e.getSceneY() - startY);
        });
    }
}
