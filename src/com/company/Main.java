package com.company;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        Lead lead = Lead.example;
        Database leadDatabase = new Database("leads.csv",Lead.idPrefix);
        leadDatabase.delete("lead_001");
        System.out.println(leadDatabase.getNextId());
    }
}
