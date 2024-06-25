package com.sailing.experiments.water_test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ReduceCSVFileSize {

    static FileWriter writer;

    public static void main(String[] args) throws IOException {
        String path = "src/main/java/com/sailing/experiments/water_test/data/";
        writer = new FileWriter(path + "firstBoatData_reduced.csv");

        ArrayList<String> data = readCSVData(path + "firstBoatData.csv");
        System.out.println(data.size());

        writer.append(data.get(0)).append("\n");

        for (int i = 1; i < (data.size()); i += 2) {
            writer.append(data.get(i)).append("\n");
        }

        writer.flush();
        writer.close();
    }

    static ArrayList<String> readCSVData(String path) {
        ArrayList<String> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }
}
