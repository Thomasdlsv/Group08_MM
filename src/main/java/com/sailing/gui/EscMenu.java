package com.sailing.gui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import static com.sailing.gui.Sailing.HEIGHT;
import static com.sailing.gui.Sailing.WIDTH;

public class EscMenu extends Pane {

    EscMenu(ObservableList<Node> children) {

        Button resume = new Button("Resume");
        resume.setPrefWidth(400);
        resume.setPrefHeight(70);
        resume.setTranslateX(WIDTH / 2 - 200);
        resume.setTranslateY(HEIGHT / 2 - 100);
        resume.setOnAction(e -> {
            System.out.println("resume");
            children.remove(this);
        });

        Button parkour = new Button("Back to Menu");
        parkour.setPrefWidth(400);
        parkour.setPrefHeight(70);
        parkour.setTranslateX(WIDTH / 2 - 200);
        parkour.setTranslateY(HEIGHT / 2 );
        parkour.setOnAction(e -> {
            System.out.println("Menu");
            getScene().setRoot(new Menu());
        });

        getChildren().addAll(resume, parkour);

    }

}
