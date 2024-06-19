package com.sailing.gui;

import com.sailing.Simulation;
import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import com.sailing.math.functions.WaterDragFunction;
import com.sailing.math.functions.WindDragFunction;
import com.sailing.math.functions.WindLiftFunction;
import com.sailing.math.functions.WindWaterForceAccelerationFunction;
import com.sailing.math.solver.RungeKutta;
import com.sailing.math.solver.Solver;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;

/**
 *
 */
class WindTunnel extends Pane {

    private final SailboatGUI sailboat;

    private final Simulation simulation;

    double windVelocity = 10;

    // for dragging
    private double startX;
    private double startY;

    LabelArrow windVelocityArrow = new LabelArrow(new Arrow(100, 100, 100, 90, Color.ALICEBLUE), "v", "tw");
    LabelArrow apparentWindArrow = new LabelArrow(new Arrow(100, 100, 100, 90, Color.AQUA), "v", "aw");
    LabelArrow accelerationArrow = new LabelArrow(new Arrow(Sailing.X_CENTER, Sailing.Y_CENTER, 100, 90, Color.RED), "a", "");
    LabelArrow velocityArrow = new LabelArrow(new Arrow(Sailing.X_CENTER, Sailing.Y_CENTER, 100, 90, Color.BLUE), "v", "");

    LabelArrow dragForceArrow = new LabelArrow(new Arrow(Sailing.X_CENTER, Sailing.Y_CENTER, 100, 90, Color.GREEN), "F", "D");
    LabelArrow liftForceArrow = new LabelArrow(new Arrow(Sailing.X_CENTER, Sailing.Y_CENTER, 100, 90, Color.YELLOW), "F", "L");
    LabelArrow windForceArrow = new LabelArrow(new Arrow(Sailing.X_CENTER, Sailing.Y_CENTER, 100, 90, Color.ORANGE), "F", "D+L");

    WindTunnel(SailboatGUI boat) {
        this.sailboat = boat;
        setBackground(new Background(new BackgroundImage(Images.background, null, null, null, null)));

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
                        drawArrows(simulation.getCurrentState());
                        drawForceArrow(simulation.getCurrentState());
                        repaintSail();
                        stats.update(simulation.getCurrentState());
                        count++;
                        if (count % 100 == 0) {
                            logState(simulation.getCurrentState());
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
                accelerationArrow,
                dragForceArrow,
                liftForceArrow,
                velocityArrow,
                windVelocityArrow, windForceArrow, apparentWindArrow);
        drawArrows(simulation.getCurrentState());
        drawForceArrow(simulation.getCurrentState());

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
                    // simulation.run(10);
                    // simulation.step();
                    logState(simulation.getCurrentState());
                    drawArrows(simulation.getCurrentState());
                }
           }
           repaintSail();
           drawForceArrow(simulation.getCurrentState());
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

    private void logState(StateSystem state) {
        System.out.println("------");
        System.out.println("Wind-Drag:  " + new WindDragFunction().eval(state, 1));
        System.out.println("Wind-Lift:  " + new WindLiftFunction().eval(state, 1));
        System.out.println("Water-Drag: " + new WaterDragFunction().eval(state, 1));
        System.out.println("Acceleration: " + new WindWaterForceAccelerationFunction().eval(state, 1));

        Vector wind = new Vector(state.getVelocity().getValue(0), state.getVelocity().getValue(1));
        Vector boat = new Vector(state.getVelocity().getValue(2), state.getVelocity().getValue(3));
        System.out.println("Wind speed: " + wind.getLength());
        System.out.println("Boat speed: " + boat.getLength());
    }

    private void drawForceArrow(StateSystem currentState) {
        Vector drag = new WindDragFunction().eval(currentState, 1);
        Vector lift = new WindLiftFunction().eval(currentState, 1);
        Vector acceleration = new WindWaterForceAccelerationFunction().eval(currentState, 1);
        Vector windForce = new WindWaterForceAccelerationFunction().calculateAccelerationWind(currentState, 1);
        Vector2D dragForceVector = new Vector2D(drag.getValue(0), drag.getValue(1));
        Vector2D liftForceVector = new Vector2D(lift.getValue(0), lift.getValue(1));
        Vector2D accelerationVector = new Vector2D(acceleration.getValue(0), acceleration.getValue(1));
        Vector2D windForceVector = new Vector2D(windForce.getValue(0), windForce.getValue(1));

        double xStart = (sailboat.getSail().localToScene(sailboat.getSail().getBoundsInLocal()).getCenterX());
        double yStart = (sailboat.getSail().localToScene(sailboat.getSail().getBoundsInLocal()).getCenterY());
        double scalar = 100 / windVelocity;

        double dragScalar = dragForceVector.getLength() * (1d/currentState.getMass());
        double liftScalar = liftForceVector.getLength() * (1d/currentState.getMass());
        double accelerationScalar = accelerationVector.getLength();
        double windScalar = windForceVector.getLength();

        dragForceArrow.setStartLengthAndAngle(new Vector2D(xStart, yStart),
                dragScalar * scalar,
                dragForceVector.toPolar().getX2());
        liftForceArrow.setStartLengthAndAngle(new Vector2D(xStart, yStart),
                liftScalar * scalar,
                liftForceVector.toPolar().getX2());
        accelerationArrow.setLengthAndAngle(
                accelerationScalar * scalar,
                accelerationVector.toPolar().getX2());
        windForceArrow.setStartLengthAndAngle(new Vector2D(xStart, yStart),
                windScalar * scalar,
                windForceVector.toPolar().getX2());
    }

    private void drawArrows(StateSystem currentState) {
        Vector2D wind = new Vector2D(currentState.getVelocity().getValue(0), currentState.getVelocity().getValue(1));
        Vector2D boat = new Vector2D(currentState.getVelocity().getValue(2), currentState.getVelocity().getValue(3));
        Vector2D apparentWind = wind.subtract(boat);

        double scalar = 9;

        windVelocityArrow.setLengthAndAngle(wind.getLength() * scalar, wind.toPolar().getX2());
        velocityArrow.setLengthAndAngle(boat.getLength() * scalar, boat.toPolar().getX2());
        apparentWindArrow.setLengthAndAngle(apparentWind.getLength() * scalar, apparentWind.toPolar().getX2());
    }

    private void repaintSail() {
        Vector2D windVelocity = simulation.getCurrentState().getWindVelocity();
        Vector2D boatVelocity = simulation.getCurrentState().getBoatVelocity();
        Vector2D apparentWind = windVelocity.subtract(boatVelocity);
        double beta = Math.toDegrees(apparentWind.toPolar().getX2()) - ((simulation.getCurrentState().getPosition().getValue(2) + simulation.getCurrentState().getPosition().getValue(3)));
        beta = (beta + 360*3) % 360;

        if (beta > 0 && beta < 180) {
           sailboat.getSail().getSailIV().setScaleX(1);
        } else {
            sailboat.getSail().getSailIV().setScaleX(-1);
        }
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
