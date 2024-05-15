package com.sailing.gui;

import com.sailing.math.data_structures.Vector2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class LabelArrow extends Group {

    private Arrow arrow;
    private TextFlow text;

    LabelArrow(Arrow arrow, String label, String subscript) {

        this.arrow = arrow;

        this.text = new TextFlow();

        Text labelText = new Text(label);
        Text subscriptText = new Text(subscript);

        labelText.setFill(arrow.getColor());
        subscriptText.setFill(arrow.getColor());

        subscriptText.setStyle("-fx-font-size: 10pt;");
        subscriptText.setTranslateY(8);

        repaint();

        text.getChildren().addAll(labelText, subscriptText);
        getChildren().addAll(arrow, text);
    }



    public void setLengthAndAngle(double length, double angleInDegrees) {

        arrow.setLengthAndAngle(length, angleInDegrees);
        repaint();

        System.out.println(arrow.getEndX() + " " + arrow.getEndY());
    }

    void repaint() {
        text.setLayoutX(arrow.getEndX() - 10);
        text.setLayoutY(arrow.getEndY() + 5);
    }

}
