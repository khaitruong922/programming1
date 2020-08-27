package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
}
