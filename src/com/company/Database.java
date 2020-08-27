package com.company;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Database {
    private String fileName;
    private String idPrefix;

    public Database(String fileName, String idPrefix) {
        this.fileName = fileName;
        this.idPrefix = idPrefix;
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
                String row = sc.nextLine();
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

    public String getLastRow() {
        File file = new File(fileName);
        try {
            FileReader fileReader = new FileReader(file);
            Scanner sc = new Scanner(fileReader);
            String lastRow = "";
            while (sc.hasNextLine()) {
                lastRow = sc.nextLine();
            }
            return lastRow;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "An error occured";
        }
    }

    public String getNextId() {
        String lastRow = getLastRow();
        String idSeparator = "_";
        if (lastRow.length() == 0) {
            return idPrefix + idSeparator + "001";
        }
        String id = lastRow.split(",")[0];
        String[] idData = id.split(idSeparator);
        String number = idData[1];
        int nextNumber = Integer.parseInt(number) + 1;
        return idPrefix + idSeparator + String.format("%03d", nextNumber);
    }
}
