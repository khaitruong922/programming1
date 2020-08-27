package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        Lead lead = Lead.example;
        Database leadDatabase = new Database("leads.csv");
        String row = leadDatabase.getRow("4");
        System.out.println("Your row:" + row);
    }
}
