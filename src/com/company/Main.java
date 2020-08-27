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
        Database leadDatabase = new Database("leads.csv");
        leadDatabase.add(lead);
        Interaction interaction = Interaction.example;
        Database interactionDatabase = new Database("interactions.csv");
        interactionDatabase.add(interaction);
    }
}
