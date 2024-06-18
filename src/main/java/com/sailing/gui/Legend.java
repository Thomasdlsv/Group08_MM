package com.sailing.gui;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Legend extends Group {

    enum Force {
        LIFT,
        DRAG,
        AERO,
        VEL,
        ACCEL,
        VEL_TW,
        VEL_AW
    }

    private double legendScale = 60;

    Legend(Force ... forces) {


        ImageView paper = new ImageView(Images.paper);
        paper.setPreserveRatio(true);
        paper.setFitHeight(getChildren().size() * legendScale);
        paper.setLayoutY(-45);
        paper.setLayoutX(-50);

        getChildren().add(paper);
        double offset = 0;
        for (Force force : forces) {

            switch (force) {
                case LIFT:
                    getChildren().add(format("F", "L", " - lift force", Color.YELLOW, offset));
                    getChildren().add(new Text(""));
                    break;
                case DRAG:
                    getChildren().add(format("F", "D", " - drag force", Color.GREEN, offset));
                    break;
                case AERO:
                    getChildren().add(format("F", "D+L", " - aerodynamic\n force", Color.ORANGE, offset));
                    break;
                case ACCEL:
                    getChildren().add(format("a", "", " - acceleration", Color.RED, offset));
                    break;
                case VEL:
                    getChildren().add(format("V", "", " - velocity", Color.BLUE, offset));
                    break;
                case VEL_TW:
                    getChildren().add(format("V", "T", " - true wind velocity", Color.WHITE, offset));
                    break;
                case VEL_AW:
                    getChildren().add(format("V", "aw", " - apparent wind\n velocity", Color.TURQUOISE, offset));
                    break;
            }

            offset += 1;
        }

        setStyle("-fx-font-size: 15pt");

    }

    private TextFlow format(String label, String subscript, String definition, Color color, double offset) {

       Text labelText = new Text(label);
       labelText.setFill(color);
       Text subscriptText = new Text(subscript);
       subscriptText.setFill(color);
       Text definitionText = new Text(definition);
       definitionText.setFill(color);

       subscriptText.setTranslateY(8);

       TextFlow res = new TextFlow(labelText, subscriptText, definitionText);
       res.setTranslateY(offset * 50);

       return res;
    }
}
