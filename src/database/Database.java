package database;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
    private String fileName;

    public Database(String fileName) {
        this.fileName = fileName;
    }

    public boolean add(IDatabaseEntity databaseEntity) {
        createFile();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName, true);
            fileWriter.write(databaseEntity.toCSV());
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean update(String id, IDatabaseEntity databaseEntity) {
        createFile();
        String[] rows = getAll();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            for (int i = 0; i < rows.length; i++) {
                String row = rows[i];
                String rowId = row.split(",")[0];
                if (rowId.equals(id)) {
                    fileWriter.write(databaseEntity.toCSV());
                    continue;
                }
                fileWriter.write(row);
                fileWriter.write("\n");
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean deleteMatch(String s, int columnIndex) {
        createFile();
        String[] rows = getAll();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(fileName);
            for (int i = 0; i < rows.length; i++) {
                String row = rows[i];
                String value = row.split(",")[columnIndex];
                if (value.equals(s)) continue;
                fileWriter.write(row);
                fileWriter.write("\n");
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean delete(String id) {
        return deleteMatch(id, 0);

    }

    public String[] getAll() {
        ArrayList<String> rows = new ArrayList<String>();
        File file = createFile();
        try {
            FileReader fileReader = new FileReader(file);
            Scanner sc = new Scanner(fileReader);
            while (sc.hasNextLine()) {
                rows.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(fileName + " not found.");
        }
        return rows.toArray(new String[rows.size()]);
    }

    public String[] getColumn(int columnIndex) {
        String[] rows = getAll();
        String[] values = new String[rows.length];
        for (int i = 0; i < rows.length; i++) {
            values[i] = (rows[i].split(",")[columnIndex]);
        }
        return values;
    }

    public String[] getAllIds() {
        return getColumn(0);
    }

    public boolean hasId(String id) {
        String[] ids = getAllIds();
        for (int i = 0; i < ids.length; i++) {
            if (id.equals(ids[i])) {
                return true;
            }
        }
        return false;
    }

    public String getRow(String id) {
        File file = createFile();
        try {
            FileReader fileReader = new FileReader(file);
            Scanner sc = new Scanner(fileReader);
            while (sc.hasNextLine()) {
                String row = sc.nextLine();
                String[] data = row.split(",");
                if (data[0].equals(id)) {
                    return row;
                }
            }
            return "Row not found";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "An error occurred";
        }
    }

    public String getLastRow() {
        File file = createFile();
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
            return "An error occurred";
        }
    }

    public String getNextIdNumber() {
        String lastRow = getLastRow();
        String idSeparator = "_";
        if (lastRow.length() == 0) {
            return "001";
        }
        String id = lastRow.split(",")[0];
        String[] idData = id.split(idSeparator);
        String number = idData[1];
        int nextNumber = Integer.parseInt(number) + 1;
        return String.format("%03d", nextNumber);
    }

    private File createFile() {
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;

    }
}
