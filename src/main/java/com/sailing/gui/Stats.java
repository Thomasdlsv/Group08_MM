package com.sailing.gui;

import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import javafx.scene.Parent;
import javafx.scene.text.Text;

public class Stats extends Parent {

    private Text time;
    private Text velocity;
    private Text position;

    public Stats() {
        time = new Text("Time: 0s");
        velocity = new Text("Velocity: 0 m/s");
        position = new Text("Position: 0, 0");

        time.setTranslateX(10);
        time.setTranslateY(10);

        velocity.setTranslateX(10);
        velocity.setTranslateY(30);

        position.setTranslateX(10);
        position.setTranslateY(50);

        getChildren().addAll(time, velocity, position);
    }

    public void update(StateSystem state) {
        setTime(state.getTime());
        setVelocity(new Vector(state.getVelocity().getValue(2), state.getVelocity().getValue(3)).getLength());
        setPosition(state.getPosition().getValue(0), state.getPosition().getValue(1));
    }

    public void setTime(double t) {
        String minutes = String.valueOf((int) t / 60);
        String seconds = String.valueOf((int) t % 60);
        time.setText("Time: " + minutes + "m " + seconds + "s");
    }

    public void setVelocity(double v) {
        velocity.setText(String.format("Velocity: %.2f m/s", v));
    }

    public void setPosition(double x, double y) {
        position.setText(String.format("Position: %.2f, %.2f", x, y));
    }
}
