package com.sailing.gui;

import com.sailing.Simulation;
import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector2D;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class Legend extends Group {

    enum Stat {
        LIFT,
        DRAG,
        AERO,
        VEL,
        ACCEL,
        VEL_TW,
        VEL_AW
    }

    private double legendScale = 60;

    Legend(Vector2D pos, String dragPos) {
        ImageView paper = getImage(pos, dragPos);
        getChildren().add(paper);
    }

    Legend(Vector2D pos, String dragPos, Stat... stats) {

        ImageView paper = getImage(pos, dragPos);

        getChildren().add(paper);
        double offset = 0;
        for (Stat stat : stats) {

            switch (stat) {
                case LIFT:
                    getChildren().add(getLegendEntry("F", "L", " - lift force", Color.YELLOW, offset));
                    getChildren().add(new Text(""));
                    break;
                case DRAG:
                    getChildren().add(getLegendEntry("F", "D", " - drag force", Color.GREEN, offset));
                    break;
                case AERO:
                    getChildren().add(getLegendEntry("F", "D+L", " - aerodynamic\n\t force", Color.ORANGE, offset));
                    break;
                case ACCEL:
                    getChildren().add(getLegendEntry("a", "", " - acceleration", Color.RED, offset));
                    break;
                case VEL:
                    getChildren().add(getLegendEntry("V", "", " - velocity", Color.BLUE, offset));
                    break;
                case VEL_TW:
                    getChildren().add(getLegendEntry("V", "T", " - true wind velocity", Color.WHITE, offset));
                    break;
                case VEL_AW:
                    getChildren().add(getLegendEntry("V", "aw", " - apparent wind\n\t velocity", Color.TURQUOISE, offset));
                    break;
            }

            offset += 1;
        }

        setStyle("-fx-font-size: 15pt");

    }

    ImageView getImage(Vector2D pos, String dragPos) {
        ImageView paper = new ImageView();
        switch (dragPos) {
            case "top":
                paper.setImage(Images.paperTop);
                break;
            case "bottom":
                paper.setImage(Images.paperBottom);
                break;
            case "left":
                paper.setImage(Images.paperLeft);
                break;
            case "right":
                paper.setImage(Images.paperRight);
                break;
        }
        paper.setPreserveRatio(true);
        paper.setFitHeight(getChildren().size() * legendScale);
        paper.setLayoutX(pos.getX1());
        paper.setLayoutY(pos.getX2());
        return paper;
    }


    private TextFlow getLegendEntry(String label, String subscript, String definition, Color color, double offset) {

       Text labelText = new Text(label);
       labelText.setFill(color);
       Text subscriptText = new Text(subscript);
       subscriptText.setFill(color);
       Text definitionText = new Text(definition);
       definitionText.setFill(color);

       subscriptText.setTranslateY(8);

       TextFlow res = new TextFlow(labelText, subscriptText, definitionText);
       res.setTranslateY(60 + (offset * 50));

       return res;
    }
}
