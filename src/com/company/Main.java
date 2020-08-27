package com.company;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SecureCacheResponse;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World");
        Lead lead = Lead.example;
        Interaction interaction = Interaction.example;
        Database leadDatabase = new Database("leads.csv",Lead.idPrefix);
        Database interactionDatabase = new Database("interactions.csv",Interaction.idPrefix);
        System.out.println(lead.toString());
        System.out.println(interaction.toString());
        System.out.println(leadDatabase.getNextId());
        System.out.println("task:    access leads    access interactions");
        System.out.println("input:         1                2");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        switch (input) {
            case "1":
                System.out.println("task for leads:        display_all    find_lead    add_lead");
                System.out.println("input:                      1              2           3");
                String input1 = sc.nextLine();
                switch (input1){
                    case "1":
                        leadDatabase.displayAll();
                        break;
                    case "2":
                        System.out.println("which id are you looking for?");
                        String inputIdLead = sc.nextLine();
                        System.out.println(leadDatabase.getRow("lead_" + inputIdLead));
                        break;
                    case "3":
                        System.out.println("");
                        break;
                }
                break;
            case "2":
                System.out.println("task for interactions:    display_all      find_interactions      add_interactions");
                System.out.println("input:                         1                  2                     3");
                String input2 = sc.nextLine();
                switch (input2){
                    case "1":
                        interactionDatabase.displayAll();
                        break;
                    case "2":
                        System.out.println("which id are you looking for?");
                        String inputIdInteraction = sc.nextLine();
                        System.out.println(interactionDatabase.getRow("inter_" + inputIdInteraction));
                        break;
                    case "3":
                        System.out.println("");
                        break;
                }
                break;
        }

    }
}
