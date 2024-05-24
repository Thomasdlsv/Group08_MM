package com.sailing.experiments.windtunnel;

import com.sailing.math.data_structures.Vector;
import com.sailing.math.functions.DragFunction;
import com.sailing.math.functions.LiftFunction;
import com.sailing.math.physics.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WindTunnelExperiment {

    public static void main(String[] args) throws IOException {
        Constants.AIR_DENSITY = 1.19;
        Constants.SAIL_AREA = 0.3 * 0.0575; // 300 mm x 57.5 mm

        String path = "src/main/java/com/sailing/experiments/windtunnel/results/";
        FileWriter writer = new FileWriter(path + "wind_tunnel.csv");
        writer.append("Angle,Wind_Speed,Lift_Calculated,Drag_Calculated,Lift_Data,Drag_Data\n");

        ArrayList<ArrayList<Double>> data = readCSVData("src/main/java/com/sailing/experiments/windtunnel/data_reduced.csv", true);
        System.out.println(data.size());

        for (int i = 0; i < data.size(); i += 1) {
            double liftData = data.get(i).get(0);
            double dragData = data.get(i).get(1);
            double windspeed = data.get(i).get(2);
            double angle = data.get(i).get(3);

            Vector position = new Vector(0, 0, 0, angle);
            Vector velocity = new Vector(windspeed, 0, 0, 0);
            double liftCalculated = - new LiftFunction().eval(position, velocity, 1, 1, 0).getValue(1);
            double dragCalculated = new DragFunction().eval(position, velocity,1, 1, 0).getValue(0);
            writer.append(angle + "," + windspeed + "," + liftCalculated + "," + dragCalculated + "," + liftData + "," + dragData + "\n");
        }

        writer.flush();
        writer.close();
    }

    static ArrayList<ArrayList<Double>> readCSVData(String path, boolean skipHeader) {
        ArrayList<ArrayList<Double>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String[] values = line.split(",");
                records.add(Stream.of(values).mapToDouble(Double::parseDouble).boxed().collect(Collectors.toCollection(ArrayList::new)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }
}
