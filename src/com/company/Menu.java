package com.company;

import java.util.Scanner;

public class Menu {
    public static void start() {
        Database leadDatabase = new Database(Lead.fileName, Lead.idPrefix);
        Database interactionDatabase = new Database(Interaction.fileName, Interaction.idPrefix);
        String[] leads = leadDatabase.getAll();
        String[] interactions = interactionDatabase.getAll();
        String leftAlignFormat = "| %-15s | %-6s |%n";
        String  displayAllFormat = "| %-7s | %-8s | %-14s | %-6s | %-13s | %-17s | %-23s |%n";
        System.out.format("+-----------------+--------+%n");
        System.out.format("| Access          | Inputs |%n");
        System.out.format("+-----------------+--------+%n");
        System.out.format(leftAlignFormat, "leads" , "1" );
        System.out.format(leftAlignFormat, "interactions", "2" );
        System.out.format("+-----------------+--------+%n");

        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        switch (input) {
            case "1"://lead
                System.out.format("+-----------------+--------+%n");
                System.out.format("| Tasks           | Inputs |%n");
                System.out.format("+-----------------+--------+%n");
                System.out.format(leftAlignFormat, "Display_all" , "1" );
                System.out.format(leftAlignFormat, "Find by ID" , "2" );
                System.out.format(leftAlignFormat, "Add_lead" , "3" );
                System.out.format("+-----------------+--------+%n");
                String input1 = sc.nextLine();
                switch (input1) {
                    case "1"://lead display all
                        System.out.format("+----------+----------+------------------------------+--------+---------------+-------------------+-------------------------+%n");
                        System.out.format("| Lead ID  |   Name   |          Birth Date          | Gender |     Phone     |       Email       |         Address         |%n");
                        System.out.format("+----------+----------+------------------------------+--------+---------------+-------------------+-------------------------+%n");
                        for (int i = 0; i < leads.length; i++) {
                            Lead lead = Lead.fromCSV(leads[i]);
                            System.out.format(displayAllFormat,lead.getId(),lead.getName(),lead.getBirthDate(),lead.isMale(),lead.getPhone(),lead.getEmail(),lead.getAddress());
                        }
                        System.out.format("+----------+----------+------------------------------+--------+---------------+-------------------+-------------------------+%n");

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
            case "2"://interaction
                System.out.format("+-----------------+--------+%n");
                System.out.format("| Tasks           | Inputs |%n");
                System.out.format("+-----------------+--------+%n");
                System.out.format(leftAlignFormat, "Display all" , "1" );
                System.out.format(leftAlignFormat, "Find by ID" , "2" );
                System.out.format(leftAlignFormat, "Add interaction" , "3" );
                System.out.format("+-----------------+--------+%n");
                String input2 = sc.nextLine();
                switch (input2) {
                    case "1":
                        for (int i = 0; i < interactions.length; i++) {
                            Interaction interaction = Interaction.fromCSV(interactions[i]);
                            System.out.println(interaction.toString());
                        }
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
