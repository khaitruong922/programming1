package com.company;

import java.util.Scanner;

public class Menu {
    public static void start() {
        Lead lead = Lead.example;
        Interaction interaction = Interaction.example;
        Database leadDatabase = new Database(Lead.fileName, Lead.idPrefix);
        Database interactionDatabase = new Database(Interaction.fileName, Interaction.idPrefix);
<<<<<<< Updated upstream
        System.out.println(lead.toString());
        System.out.println(interaction.toString());
        System.out.println(leadDatabase.getNextId());
        System.out.println("task:    access leads    access interactions");
        System.out.println("input:         1                2");
=======
        String[] leads = leadDatabase.getAll();
        String[] interactions = interactionDatabase.getAll();
        //all format
        String leftAlignFormat = "| %-15s | %-6s |%n";
        String displayAllFormatLead = "| %-7s | %-8s | %-14s | %-6s | %-13s | %-17s | %-23s |%n";
        String displayAllFormatInteraction = "| %-15s | %-30s | %-8s | %-12s | %-9s |%n";

        System.out.format("+-----------------+--------+%n");
        System.out.format("| Access          | Inputs |%n");
        System.out.format("+-----------------+--------+%n");
        System.out.format(leftAlignFormat, "leads", "1");
        System.out.format(leftAlignFormat, "interactions", "2");
        System.out.format("+-----------------+--------+%n");

>>>>>>> Stashed changes
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        switch (input) {
<<<<<<< Updated upstream
            case "1":
                System.out.println("task for leads:        display_all    find_lead    add_lead");
                System.out.println("input:                      1              2           3");
                String input1 = sc.nextLine();
                switch (input1) {
                    case "1":
                        leadDatabase.getAll();
=======
            case "1"://lead
                System.out.format("+-----------------+--------+%n");
                System.out.format("| Tasks           | Inputs |%n");
                System.out.format("+-----------------+--------+%n");
                System.out.format(leftAlignFormat, "Display_all", "1");
                System.out.format(leftAlignFormat, "Find by ID", "2");
                System.out.format(leftAlignFormat, "Add_lead", "3");
                System.out.format("+-----------------+--------+%n");
                String input1 = sc.nextLine();
                switch (input1) {
                    case "1"://lead display all
                        System.out.format("+----------+----------+------------------------------+--------+---------------+-------------------+-------------------------+%n");
                        System.out.format("| Lead ID  |   Name   |          Birth Date          | Gender |     Phone     |       Email       |         Address         |%n");
                        System.out.format("+----------+----------+------------------------------+--------+---------------+-------------------+-------------------------+%n");
                        for (int i = 0; i < leads.length; i++) {
                            Lead lead = Lead.fromCSV(leads[i]);
                            System.out.format(
                                    displayAllFormatLead,
                                    lead.getId(),
                                    lead.getName(),
                                    DateParser.dateToString(lead.getBirthDate()),
                                    lead.isMale() ? "Male" : "Female",
                                    lead.getPhone(),
                                    lead.getEmail(),
                                    lead.getAddress());
                        }
                        System.out.format("+----------+----------+------------------------------+--------+---------------+-------------------+-------------------------+%n");

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
            case "2":
                System.out.println("task for interactions:    display_all      find_interactions      add_interactions");
                System.out.println("input:                         1                  2                     3");
                String input2 = sc.nextLine();
                switch (input2) {
                    case "1":
                        interactionDatabase.getAll();
=======
            case "2"://interaction
                System.out.format("+-----------------+--------+%n");
                System.out.format("| Tasks           | Inputs |%n");
                System.out.format("+-----------------+--------+%n");
                System.out.format(leftAlignFormat, "Display all", "1");
                System.out.format(leftAlignFormat, "Find by ID", "2");
                System.out.format(leftAlignFormat, "Add interaction", "3");
                System.out.format("+-----------------+--------+%n");
                String input2 = sc.nextLine();
                switch (input2) {
                    case "1": // interaction display all
                        System.out.format("+-----------------+--------------------------------+----------+--------------+-----------+%n");// 17 18 10 10 11
                        System.out.format("| Interaction ID  |        Interaction Date        | Lead ID  |     Mean     | Potential |%n");
                        System.out.format("+-----------------+--------------------------------+----------+--------------+-----------+%n");
                        for (int i = 0; i < interactions.length; i++) {
                            Interaction interaction = Interaction.fromCSV(interactions[i]);
                            System.out.format(
                                    displayAllFormatInteraction,
                                    interaction.getId(),
                                    DateParser.dateToString(interaction.getInteractionDate()),
                                    interaction.getLeadId(),
                                    interaction.getMean(),
                                    interaction.getPotential());
                        }
                        System.out.format("+-----------------+--------------------------------+----------+--------------+-----------+%n");
>>>>>>> Stashed changes
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
