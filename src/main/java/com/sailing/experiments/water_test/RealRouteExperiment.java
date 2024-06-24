package com.sailing.experiments.water_test;

import com.sailing.Simulation;
import com.sailing.math.StateSystem;
import com.sailing.math.data_structures.Vector;
import com.sailing.math.data_structures.Vector2D;
import com.sailing.math.physics.Coefficients;
import com.sailing.math.physics.Constants;
import com.sailing.math.solver.RungeKutta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RealRouteExperiment {

    static FileWriter writer;
    static boolean write = true;
    private static final double EARTH_RADIUS_METERS = 6378137.0;

    public static void main(String[] args) throws IOException {
        String path = "src/main/java/com/sailing/experiments/water_test/results/";
        if (write) writer = new FileWriter(path + "secondBoatSim.csv");
        if (write) writer.append("BaseDateTime,LAT,LON,distance,speed,angle\n");

        ArrayList<ArrayList<String>> data = readCSVData("src/main/java/com/sailing/experiments/water_test/data/secondBoatData.csv", true);
        System.out.println(data.size());

        Coefficients.reynoldsNumber = Coefficients.ReynoldsNumber.RE40K;
        Constants.SAIL_AREA = 5 * 10;

        double totalDistanceSim = Double.parseDouble(data.get(0).get(4));

        System.out.println(toTime(data.get(0).get(1)));

        StateSystem initState = rowToStateSystem(data, 0);
        StateSystem lastState = initState;
        Simulation simulation = new Simulation(new RungeKutta(), initState, 0.01);
        double lastCx = Double.parseDouble(data.get(0).get(3));
        double lastCy = Double.parseDouble(data.get(0).get(2));

        for (int i = 0; i < (data.size()-1); i += 1) {
            System.out.println("-----------------");
            String time = data.get(i).get(1);
            double cx = Double.parseDouble(data.get(i).get(3));
            double cy = Double.parseDouble(data.get(i).get(2));
            double cx2 = Double.parseDouble(data.get(i + 1).get(3));
            double cy2 = Double.parseDouble(data.get(i + 1).get(2));

            double angle = (Math.toDegrees(calculateAngleFromCoordinates(cy, cx, cy2, cx2)) + 360 + 90) % 360;
            simulation.setBoatAngle(angle);

            double distance = Double.parseDouble(data.get(i+1).get(4));

            double windSpeed = Double.parseDouble(data.get(i).get(6));
            double windAngle = Math.toRadians(transformWindToPolar(Double.parseDouble(data.get(i).get(7))));
            Vector2D windVelocity = new Vector2D(kmhToMs(windSpeed), windAngle, true).toCartesian();
            simulation.setWindVelocity(windVelocity);
            // simulation.setBoatVelocity(new Vector2D(kmhToMs(Double.parseDouble(data.get(i).get(5))), angle, true).toCartesian());

            double step = (toTime(data.get(i + 1).get(1)) - (double) toTime(time)) / 1000;
            simulation.runUntil(simulation.getCurrentState().getTime() + step);

            StateSystem currentState = simulation.getCurrentState();

            Vector lastPos = new Vector(lastState.getPosition().getValue(0), lastState.getPosition().getValue(1));
            Vector simPos = new Vector(currentState.getPosition().getValue(0), currentState.getPosition().getValue(1));
            totalDistanceSim += lastPos.subtract(simPos).getLength();

            double newCx = increaseLonByMeters(lastCy, lastCx, currentState.getPosition().getValue(0) - lastState.getPosition().getValue(0));
            double newCy = increaseLatByMeters(lastCy, currentState.getPosition().getValue(1) - lastState.getPosition().getValue(1));

            System.out.println("Time:                " + fromTime((long) (toTime(time) + (step*1000))));

            // System.out.printf( "Angle Wind:          %.6f%n", windAngle);
            // System.out.printf( "Distance recorded:   %.4f m%n", thisDistance);
            // System.out.printf( "Distance simulated:  %.4f m%n", lastPos.subtract(simPos).getLength());
            // System.out.printf( "Total Distance rec:  %.4f m%n", distance);
            // System.out.printf( "Total Distance sim:  %.4f m%n", totalDistanceSim);
            // System.out.printf( "Wind speed:          %.6f%n", windSpeed);
            // System.out.printf( "Speed recorded:      %.4f km/h%n", Double.parseDouble(data.get(i).get(5)));
            // System.out.printf( "Speed simulated:     %.4f km/h%n", msToKmh(simulation.getCurrentState().getBoatVelocity().getLength()));
            // System.out.println("Actual position:     " + cy + ", " + cx);
            // System.out.println("Simulated position:  " + newCy + ", " + newCx);

            if (write) writer.append(
                    fromTime((long)simulation.getCurrentState().getTime()*1000) + ","
                            + newCy + "," + newCx + ","
                            + totalDistanceSim + ","
                            + msToKmh(simulation.getCurrentState().getBoatVelocity().getLength()) + ","
                            + angle +"\n");

            lastState = currentState;
            lastCx = newCx;
            lastCy = newCy;
        }

        if (write) writer.flush();
        if (write) writer.close();
    }

    static StateSystem rowToStateSystem(ArrayList<ArrayList<String>> data, int i) {
        double cy0 = Double.parseDouble(data.get(i).get(2));
        double cx0 = Double.parseDouble(data.get(i).get(3));
        double cy1 = Double.parseDouble(data.get(i + 1).get(2));
        double cx1 = Double.parseDouble(data.get(i + 1).get(3));
        double angle = calculateAngleFromCoordinates(cy0, cx0, cy1, cx1);

        double speed = Double.parseDouble(data.get(i).get(5));
        Vector2D initVelocity = new Vector2D(kmhToMs(speed), angle, true).toCartesian();

        double windSpeed = Double.parseDouble(data.get(0).get(6));
        double windAngle = Double.parseDouble(data.get(0).get(7));
        Vector2D windVelocity = new Vector2D(kmhToMs(windSpeed), windAngle, true).toCartesian();

        return new StateSystem(
                new Vector(0, 0, angle, -80),
                new Vector(windVelocity.getX1(), windVelocity.getX2(), initVelocity.getX1(), initVelocity.getX2()),
                new Vector(0, 0),
                4000,
                (double) toTime(data.get(0).get(1)) / 1000
        );
    }

    static ArrayList<ArrayList<String>> readCSVData(String path, boolean skipHeader) {
        ArrayList<ArrayList<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                String[] values = line.split(",");
                records.add(Stream.of(values).collect(Collectors.toCollection(ArrayList::new)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }

    private static double increaseLonByMeters(double startLat, double startLon, double meters) {
        double latRad = Math.toRadians(startLat);
        double metersPerDegree = (Math.PI / 180) * EARTH_RADIUS_METERS * Math.cos(latRad);
        double deltaLon = meters / metersPerDegree;
        return startLon + deltaLon;
    }

    private static double increaseLatByMeters(double startLat, double meters) {
        double metersPerDegree = (Math.PI / 180) * EARTH_RADIUS_METERS;
        double deltaLat = meters / metersPerDegree;
        return startLat + deltaLat;
    }

    private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS_METERS * c;
    }

    private static double kmhToMs(double kmh) {
        return kmh / 3.6;
    }

    private static double msToKmh(double ms) {
        return ms * 3.6;
    }

    public static double transformWindToPolar(double windAngle) {
        return (450 - windAngle + 180) % 360;
    }

    private static double calculateAngleFromCoordinates(double startLat, double startLon, double endLat, double endLon) {
        double deltaLon = calculateDistance(startLat, startLon, startLat, endLon);
        double deltaLat = calculateDistance(startLat, startLon, endLat, startLon);

        if (endLat > startLat) deltaLat *= -1;
        if (endLon < startLon) deltaLon *= -1;

        return new Vector2D(deltaLon, deltaLat, false).toPolar().getX2();
    }

    private static long toTime(String time) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return f.parse(time).getTime();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String fromTime(long time) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(time);
    }
}
