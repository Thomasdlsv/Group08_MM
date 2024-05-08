package com.sailing.gui;

import com.sailing.math.data_structures.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Arrow extends Path {

    private static final double arrowHeadSize = 5.0;
    double startX, startY, endX, endY;
    Color color;

    public Arrow(double startX, double startY, double endX, double endY, Color color) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;

        rePaint();
    }

    private void rePaint() {
        getElements().removeAll(getElements());
        setFill(color);
        strokeProperty().bind(fillProperty());
        setStrokeWidth(4);

        // Line
        getElements().add(new MoveTo(startX, startY));
        getElements().add(new LineTo(endX, endY));

        // Head
        double angle = new Vector2D(endX - startX, endY - startY).toPolar().getX2() - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;
        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + endY;

        getElements().add(new LineTo(x1, y1));
        getElements().add(new LineTo(x2, y2));
        getElements().add(new LineTo(endX, endY));
    }

    public Arrow(Vector2D start, Vector2D end, Color color){
        this(
                start.getX1(),
                start.getX2(),
                end.getX1(),
                end.getX2(),
                color);
    }

    public Arrow(Vector2D start, double length, double angleInDegrees, Color color){
        this(
                start.getX1(),
                start.getX2(),
                start.add(new Vector2D(length, angleInDegrees, true).toCartesian()).getX1(),
                start.add(new Vector2D(length, angleInDegrees, true).toCartesian()).getX2(),
                color);
    }

    public void setStart(Vector2D start){
        this.startX = start.getX1();
        this.startY = start.getX2();
        rePaint();
    }

    public void setEnd(Vector2D end){
        this.endX = end.getX1();
        this.endY = end.getX2();
        rePaint();
    }

    public void setColor(Color color){
        this.color = color;
        rePaint();
    }

    public void setLength(double length){
        Vector2D start = new Vector2D(startX, startY);
        Vector2D newEnd = start.add(new Vector2D(
                length,
                new Vector2D(endX - startX, endY - startY).toPolar().getX2(),
                true).toCartesian());
        this.endX = newEnd.getX1();
        this.endY = newEnd.getX2();
        rePaint();
    }

    public void setAngle(double angleInDegrees){
        Vector2D start = new Vector2D(startX, startY);
        Vector2D newEnd = start.add(new Vector2D(
                new Vector2D(endX - startX, endY - startY).toPolar().getX1(),
                angleInDegrees,
                true).toCartesian());
        this.endX = newEnd.getX1();
        this.endY = newEnd.getX2();
        rePaint();
    }

    public void setStartAndEnd(Vector2D start, Vector2D end){
        this.startX = start.getX1();
        this.startY = start.getX2();
        this.endX = end.getX1();
        this.endY = end.getX2();
        rePaint();
    }

    public void setLengthAndAngle(double length, double angleInDegrees){
        Vector2D start = new Vector2D(startX, startY);
        Vector2D newEnd = start.add(new Vector2D(
                length,
                angleInDegrees,
                true).toCartesian());
        this.endX = newEnd.getX1();
        this.endY = newEnd.getX2();
        rePaint();
    }
}