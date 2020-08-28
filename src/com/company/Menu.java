package com.company;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class Menu {
    public static void start() throws Exception {
        Database leadDatabase = new Database(Lead.fileName);
        Database interactionDatabase = new Database(Interaction.fileName);
        String[] leads = leadDatabase.getAll();
        String[] interactions = interactionDatabase.getAll();

        //finding longest string for each lead's element
        int fLeadId = 0, fName = 0, fBirthday = 0, fPhone = 0, fEmail = 0, fAddress = 0;
        for (int i = 0; i < leads.length; i++) {
            Lead lead = Lead.fromCSV(leads[i]);
            fLeadId = Math.max(fLeadId, lead.getId().length());
            fName = Math.max(fName, lead.getName().length());
            fBirthday = Math.max(fBirthday, DateParser.dateToString(lead.getBirthDate()).length());
            fPhone = Math.max(fPhone, lead.getPhone().length());
            fEmail = Math.max(fEmail, lead.getEmail().length());
            fAddress = Math.max(fAddress, lead.getAddress().length());
        }

        //all format
        String leftAlignFormat = "| %-15s | %-6s |%n";
        String displayAllFormatLead = "| %-" + fLeadId + "s | %-" + fName + "s | %-" + fBirthday + "s | %-6s | %-" + fPhone + "s | %-" + fEmail + "s | %-" + fAddress + "s |%n";
        String displayAllFormatInteraction = "| %-15s | %-30s | %-8s | %-12s | %-9s |%n";

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
                        String borderForDisplayAllLead = String.format(displayAllFormatLead, "", "", "", "", "", "", "").replace(" ", "-").replace("|", "+");
                        System.out.print(borderForDisplayAllLead);
                        System.out.format(displayAllFormatLead, "Lead ID", "Name", "Birth Date", "Gender", "Phone", "Email", "Address");
                        System.out.print(borderForDisplayAllLead);
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

                        break;
                    case "2":
                        System.out.println("which id are you looking for?");
                        String inputIdLead = sc.nextLine();
                        System.out.println(leadDatabase.getRow("lead_" + inputIdLead));
                        break;
                    case "3"://lead add
                        System.out.println("Adding a lead");
                        System.out.print("Name                          | ");
                        String name = sc.nextLine();
                        Date birthDate = null;
                        boolean valid = true;
                        do {
                            try {
                                System.out.print("Birth Date (YYYY-MM-DD)    | ");
                                String birthDateString = sc.nextLine();
                                birthDate = DateParser.stringToDate(birthDateString);
                            } catch (ParseException e) {
                                System.out.println("Invalid format. Please try again.");
                            }
                        } while (birthDate == null);
                        boolean isMale = false;
                        do {
                            if (!valid) {
                                System.out.println("Invalid input. Please type 0 for female and 1 for male.");
                            }
                            System.out.print("Gender (0: female, 1: male)                | ");
                            String choice = sc.nextLine();
                            valid = (choice.equals("0") || choice.equals("1"));
                            isMale = choice.equals("1");
                        } while (!valid);

                        System.out.print("Phone                         | ");
                        String phone = sc.nextLine();
                        System.out.print("Email                         | ");
                        String email = sc.nextLine();
                        System.out.print("Address                       | ");
                        String address = sc.nextLine();
                        Lead addLead = new Lead(Lead.idPrefix + leadDatabase.getNextId(), name, birthDate, isMale, phone, email, address);
                        try {
                            leadDatabase.add(addLead);
                            System.out.println("Lead added successfully");
                        } catch (IOException e) {
                            System.out.println("Error occured when trying to add a lead.");
                            e.printStackTrace();
                        }
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
