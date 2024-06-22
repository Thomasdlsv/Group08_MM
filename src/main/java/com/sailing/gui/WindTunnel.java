package com.sailing.gui;

import com.sailing.Simulation;
import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import com.sailing.math.functions.DragFunction;
import com.sailing.math.functions.LiftFunction;
import com.sailing.math.functions.WindForceAccelerationFunction;
import com.sailing.math.solver.RungeKutta;
import com.sailing.math.solver.Solver;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 */
class WindTunnel extends Pane {

    private final SailboatGUI sailboat;

    private final Simulation simulation;

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
        makeDraggable(legend, false, true, null, new double[]{20, 340});
        getChildren().add(legend);

        Vector position = new Vector(0, 0, -90, 0);
        Vector velocity = new Vector(0, 1, 0, 0).normalize();
        Vector acceleration = new Vector(0, 0);
        double mass = 100;
        double time = 0;
        StateSystem stateSystem = new StateSystem(position, velocity, acceleration, mass, time);
        Solver solver = new RungeKutta();

        simulation = new Simulation(solver, stateSystem, 1);

        getChildren().addAll(accelerationArrow, dragForceArrow, liftForceArrow,
                // velocityArrow, // TODO: reactivate this line to show the boat's velocity
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
                    // simulation.step(); // TODO: reactivate this line for the next phase
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

    private void drawForceArrow(StateSystem currentState) {
        Vector drag = new DragFunction().eval(currentState, 1);
        Vector lift = new LiftFunction().eval(currentState, 1);
        Vector acceleration = new WindForceAccelerationFunction().eval(currentState, 1);
        Vector windForce = new WindForceAccelerationFunction().calculateAcceleration(currentState, 1);
        Vector2D dragForceVector = new Vector2D(drag.getValue(0), drag.getValue(1));
        Vector2D liftForceVector = new Vector2D(lift.getValue(0), lift.getValue(1));
        Vector2D accelerationVector = new Vector2D(acceleration.getValue(0), acceleration.getValue(1));
        Vector2D windForceVector = new Vector2D(windForce.getValue(0), windForce.getValue(1));

        double scalar = 900;

        double radius = sailboat.getSail().getHeight() / 4;

        double xStart = (sailboat.getSail().localToScene(sailboat.getSail().getBoundsInLocal()).getCenterX());

        double yStart = (sailboat.getSail().localToScene(sailboat.getSail().getBoundsInLocal()).getCenterY());


        dragForceArrow.setStartLengthAndAngle(new Vector2D(xStart, yStart),
                dragForceVector.getLength() * scalar * (1d/currentState.getMass()),
                dragForceVector.toPolar().getX2());
        liftForceArrow.setStartLengthAndAngle(new Vector2D(xStart, yStart),
                liftForceVector.getLength() * scalar * (1d/currentState.getMass()),
                liftForceVector.toPolar().getX2());
        accelerationArrow.setLengthAndAngle(
                accelerationVector.getLength() * scalar,
                accelerationVector.toPolar().getX2());
        windForceArrow.setStartLengthAndAngle(new Vector2D(xStart, yStart),
                windForceVector.getLength() * scalar,
                windForceVector.toPolar().getX2());
    }

    private void drawArrows(StateSystem currentState) {
        Vector2D wind = new Vector2D(currentState.getVelocity().getValue(0), currentState.getVelocity().getValue(1));
        Vector2D boat = new Vector2D(currentState.getVelocity().getValue(2), currentState.getVelocity().getValue(3));
        Vector2D acceleration = new Vector2D(currentState.getAcceleration().getValue(0), currentState.getAcceleration().getValue(1));
        Vector2D apparentWind = wind.subtract(boat);

        double scalar = 90;

        windVelocityArrow.setLengthAndAngle(wind.getLength() * scalar, wind.toPolar().getX2());
        accelerationArrow.setLengthAndAngle(acceleration.toPolar().getX1() * scalar, acceleration.toPolar().getX2());
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


    void makeDraggable(Node node, boolean x, boolean y, double[] xBounds, double[] yBounds) {
        if (x && y) {

            node.setOnMousePressed(e -> {
                startY = e.getSceneY() - node.getTranslateY();
                startX = e.getSceneX() - node.getTranslateX();
            });
            node.setOnMouseDragged(e -> {
                node.setTranslateY(e.getSceneY() - startY);
                node.setTranslateX(e.getSceneX() - startX);
                if (xBounds != null) {
                    if (node.getTranslateX() > xBounds[1]) node.setTranslateX(xBounds[1]);
                    if (node.getTranslateX() < xBounds[0]) node.setTranslateX(xBounds[0]);
                }
                if (yBounds != null) {
                    if (node.getTranslateY() > yBounds[1]) node.setTranslateY(yBounds[1]);
                    if (node.getTranslateY() < yBounds[0]) node.setTranslateY(yBounds[0]);
                }
            });
        } else if (x) {
            node.setOnMousePressed(e -> {
                    startX = e.getSceneX() - node.getTranslateX();
            });

            node.setOnMouseDragged(e -> {
                node.setTranslateX(e.getSceneX() - startX);
                    if (xBounds != null) {
                        if (node.getTranslateX() > xBounds[1]) node.setTranslateX(xBounds[1]);
                        if (node.getTranslateX() < xBounds[0]) node.setTranslateX(xBounds[0]);
                    }
            });

        } else if (y) {
            System.out.println("im here");
            node.setOnMousePressed(e -> {
                startY = e.getSceneY() - node.getTranslateY();
                System.out.println(startY);
            });

            node.setOnMouseDragged(e -> {
                node.setTranslateY(e.getSceneY() - startY);
                if (yBounds != null) {
                    if (node.getTranslateY() > yBounds[1]) node.setTranslateY(yBounds[1]);
                    if (node.getTranslateY() < yBounds[0]) node.setTranslateY(yBounds[0]);
                }
            });

        }

    }

}
