package com.company;

import java.util.Scanner;

public class Menu {
    public static void start() {
        Lead lead = Lead.example;
        Interaction interaction = Interaction.example;
        Database leadDatabase = new Database(Lead.fileName, Lead.idPrefix);
        Database interactionDatabase = new Database(Interaction.fileName, Interaction.idPrefix);
        String leftAlignFormat = "| %-15s | %-6s |%n";
        String  displayAllFormat = "| %-23s |%n";
        System.out.format("+-----------------+--------+%n");
        System.out.format("| Access          | Inputs |%n");
        System.out.format("+-----------------+--------+%n");
        System.out.format(leftAlignFormat, "leads" , "1" );
        System.out.format(leftAlignFormat, "interactions", "2" );
        System.out.format("+-----------------+--------+%n");

        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        switch (input) {
            case "1":
                System.out.format("+-----------------+--------+%n");
                System.out.format("| Tasks           | Inputs |%n");
                System.out.format("+-----------------+--------+%n");
                System.out.format(leftAlignFormat, "Display_all" , "1" );
                System.out.format(leftAlignFormat, "Find by ID" , "2" );
                System.out.format(leftAlignFormat, "Add_lead" , "3" );
                System.out.format("+-----------------+--------+%n");
                String input1 = sc.nextLine();
                switch (input1) {
                    case "1":
                        System.out.format("+-----------------+--------+%n");
                        System.out.format("| Tasks           | Inputs |%n");
                        System.out.format("+-----------------+--------+%n");
                        for (String leadString: leadDatabase.getAll()
                             ) {
                            System.out.format(displayAllFormat,leadString);
                        }
                        System.out.format("+-----------------+--------+%n");

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
                        interactionDatabase.getAll();
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
