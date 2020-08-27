package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Database {
    private String fileName;

    public Database(String fileName) {
        this.fileName = fileName;
    }

    public void add(IDatabaseEntity databaseEntity) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        fileWriter.write(databaseEntity.toCSV());
        fileWriter.close();
    }

    public void displayAll() {
        File file = new File(fileName);
        try {
            FileReader fileReader = new FileReader(file);
            Scanner sc = new Scanner(fileReader);
            while (sc.hasNextLine()) {
                System.out.println(sc.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getRow(String id) {
        File file = new File(fileName);
        try {
            FileReader fileReader = new FileReader(file);
            Scanner sc = new Scanner(fileReader);
            while (sc.hasNextLine()) {
                String row = sc.next();
                String[] data = row.split(",");
                System.out.println(Arrays.toString(data));
                if (data[0].equals(id)) {
                    System.out.println("Found");
                    return row;
                }
            }
            return "Row not found";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "An error occured";
        }
    }
}
