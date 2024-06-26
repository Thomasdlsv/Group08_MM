package com.sailing.gui;

import com.sailing.math.data_structures.Vector2D;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class Target extends Parent {

    Text text;
    Vector2D position;
    boolean hit = false;
    boolean open = false;

    public Target(int number, Vector2D position) {

        ImageView target = new ImageView(Images.target);
        target.setFitWidth(90);
        target.setFitHeight(90);

        target.setTranslateX(Sailing.X_CENTER + position.getX1() * 10 - 48);
        target.setTranslateY(Sailing.Y_CENTER + position.getX2() * 10 - 28 - 35);

        getChildren().add(target);

        this.text = new Text("" + number);
        getChildren().add(this.text);
        this.position = position;

        this.text.setTranslateX(Sailing.X_CENTER + position.getX1() * 10);
        this.text.setTranslateY(Sailing.Y_CENTER + position.getX2() * 10 - 35);

        this.text.setStyle("-fx-font-size: 27pt;");
        recolor();
    }

    public void checkHit(Vector2D position) {
        if (!open) return;
        if (this.position.subtract(position).getLength() < 5) {
            hit = true;
        }
        recolor();
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
        recolor();
    }

    public void setOpen(boolean open) {
        this.open = open;
        recolor();
    }

    private void recolor() {
        if (hit) {
            text.setFill(javafx.scene.paint.Color.GREEN);
        } else if (open) {
            text.setFill(javafx.scene.paint.Color.RED);
        } else {
            text.setFill(javafx.scene.paint.Color.BLACK);
        }
    }
}
