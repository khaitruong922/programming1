package com.company;

import javax.swing.text.DateFormatter;
import java.text.ParseException;
import java.util.Scanner;

public class Menu {
    public static void start() throws Exception {
        Database leadDatabase = new Database(Lead.fileName, Lead.idPrefix);
        Database interactionDatabase = new Database(Interaction.fileName, Interaction.idPrefix);
        String[] leads = leadDatabase.getAll();
        String[] interactions = interactionDatabase.getAll();
        Boolean valid;
        Boolean gender;
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

        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        switch (input) {
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
                            System.out.format(displayAllFormatLead, lead.getId(), lead.getName(),DateParser.dateToString(lead.getBirthDate()), lead.isMale(), lead.getPhone(), lead.getEmail(), lead.getAddress());
                        }
                        System.out.format("+----------+----------+------------------------------+--------+---------------+-------------------+-------------------------+%n");

                        break;
                    case "2"://lead find by ID
                        System.out.println("which id are you looking for?");
                        String inputIdLead = sc.nextLine();
                        System.out.println(leadDatabase.getRow("lead_" + inputIdLead));
                        break;
                    case "3"://lead add
                        System.out.println("Adding a lead");
                        System.out.print("Lead ID                       | ");
                        String leadID1 = sc.nextLine();
                        System.out.print("Name                          | ");
                        String name = sc.nextLine();
                        System.out.print("Birth Date(year-month-day)    | ");
                        String bDate = sc.nextLine();
                        do {
                            System.out.print("Gender                        | ");
                            String temp = sc.nextLine();
                            valid = (temp.equals("male") || temp.equals("female"));
                            gender = temp.equals("male");
                        } while (!valid);

                        System.out.print("Phone                         | ");
                        String phone = sc.nextLine();
                        System.out.print("Email                         | ");
                        String email = sc.nextLine();
                        System.out.print("Address                       | ");
                        String address = sc.nextLine();
                        Lead addLead = new Lead(leadID1, name, DateParser.stringToDate(bDate), gender, phone, email, address);
                        leadDatabase.add(addLead);
                        break;
                }
                break;
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
                            System.out.format(displayAllFormatInteraction, interaction.getId(), interaction.getInteractionDate(), interaction.getLeadId(), interaction.getMean(), interaction.getPotential());
                        }
                        System.out.format("+-----------------+--------------------------------+----------+--------------+-----------+%n");
                        break;
                    case "2"://interaction find by ID
                        System.out.println("which id are you looking for?");
                        String inputIdInteraction = sc.nextLine();
                        System.out.println(interactionDatabase.getRow("inter_" + inputIdInteraction));
                        break;
                    case "3"://interaction add
                        System.out.println("");
                        break;
                }
                break;
        }
    }
}
