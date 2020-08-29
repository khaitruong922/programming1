package com.company;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class Menu {
    private static Database leadDatabase = new Database(Lead.fileName);
    private static Database interactionDatabase = new Database(Interaction.fileName);
    private static String leftAlignFormat = "| %-15s | %-6s |%n";

    private static void printMenuOptions() {
        System.out.format("+-----------------+--------+%n");
        System.out.format("| Access          | Inputs |%n");
        System.out.format("+-----------------+--------+%n");
        System.out.format(leftAlignFormat, "Leads", "1");
        System.out.format(leftAlignFormat, "Interactions", "2");
        System.out.format(leftAlignFormat, "Exit", "3");
        System.out.format("+-----------------+--------+%n");
    }

    private static void printLeadMenuOptions() {
        System.out.format("+-----------------+--------+%n");
        System.out.format("| Tasks           | Inputs |%n");
        System.out.format("+-----------------+--------+%n");
        System.out.format(leftAlignFormat, "Display_all", "1");
        System.out.format(leftAlignFormat, "Find by ID", "2");
        System.out.format(leftAlignFormat, "Add lead", "3");
        System.out.format("+-----------------+--------+%n");
    }

    private static void printInteractionMenuOptions() {
        System.out.format("+-----------------+--------+%n");
        System.out.format("| Tasks           | Inputs |%n");
        System.out.format("+-----------------+--------+%n");
        System.out.format(leftAlignFormat, "Display all", "1");
        System.out.format(leftAlignFormat, "Find by ID", "2");
        System.out.format(leftAlignFormat, "Add interaction", "3");
        System.out.format("+-----------------+--------+%n");
    }

    public static void start() throws Exception {


        boolean shouldExit = false;
        while (!shouldExit) {
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
            //find longest string for each interaction's element
            int fInteractionID = 0, fInteractionDate = 0, fInteractionLeadID = 0, fMean = 0; //potential = 9
            for (int i = 0; i < interactions.length; i++) {
                Interaction interaction = Interaction.fromCSV(interactions[i]);
                fInteractionID = Math.max(Math.max(fInteractionID, interaction.getId().length()), "Interaction ID".length());
                fInteractionDate = Math.max(Math.max(fInteractionDate, DateParser.dateToString(interaction.getInteractionDate()).length()), "Interaction Date".length());
                fInteractionLeadID = Math.max(fInteractionLeadID, interaction.getLeadId().length());
                fMean = Math.max(Math.max(fMean, interaction.getMean().length()), "Lead ID".length());
            }

            //all format

            String displayAllLeadFormat = "| %-" + fLeadId + "s | %-" + fName + "s | %-" + fBirthday + "s | %-6s | %-" + fPhone + "s | %-" + fEmail + "s | %-" + fAddress + "s |%n";
            String displayAllFormatInteraction = "| %-" + fInteractionID + "s | %-" + fInteractionDate + "s | %-" + fInteractionLeadID + "s | %-" + fMean + "s | %-9s |%n";
            String borderForDisplayAllLead = String.format(displayAllLeadFormat, "", "", "", "", "", "", "").replace(" ", "-").replace("|", "+");
            String borderForDisplayAllInteraction = String.format(displayAllFormatInteraction, "", "", "", "", "").replace(" ", "-").replace("|", "+");
            printMenuOptions();

            Scanner sc = new Scanner(System.in);

            String menuInput = sc.nextLine();

            switch (menuInput) {
                case "1": {
                    printLeadMenuOptions();
                    String input = sc.nextLine();
                    switch (input) {
                        case "1": {
                            System.out.print(borderForDisplayAllLead);
                            System.out.format(displayAllLeadFormat, "Lead ID", "Name", "Birth Date", "Gender", "Phone", "Email", "Address");
                            System.out.print(borderForDisplayAllLead);
                            for (int i = 0; i < leads.length; i++) {
                                Lead lead = Lead.fromCSV(leads[i]);
                                System.out.format(
                                        displayAllLeadFormat,
                                        lead.getId(),
                                        lead.getName(),
                                        DateParser.dateToString(lead.getBirthDate()),
                                        lead.isMale() ? "Male" : "Female",
                                        lead.getPhone(),
                                        lead.getEmail(),
                                        lead.getAddress());
                            }
                            System.out.print(borderForDisplayAllLead);
                            break;
                        }
                        case "2": {
                            System.out.println("which id are you looking for?");
                            String inputIdLead = sc.nextLine();
                            System.out.println(leadDatabase.getRow("lead_" + inputIdLead));
                            break;
                        }
                        case "3": {
                            System.out.println("Adding a lead");
                            System.out.print("Name                          | ");
                            String name = sc.nextLine();
                            Date birthDate = null;

                            do {
                                try {
                                    System.out.print("Birth Date (YYYY-MM-DD)    | ");
                                    String birthDateString = sc.nextLine();
                                    birthDate = DateParser.stringToDate(birthDateString);
                                } catch (ParseException e) {
                                    System.out.println("Invalid format. Please try again.");
                                }
                            } while (birthDate == null);
                            boolean valid = true;
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
                    }
                    break;
                }
                case "2": {
                    printInteractionMenuOptions();
                    String input = sc.nextLine();
                    switch (input) {
                        case "1": {
                            System.out.format(borderForDisplayAllInteraction);
                            System.out.format(displayAllFormatInteraction, "Interaction ID", "Interaction Date", "Lead ID", "Mean ", "Potential");
                            System.out.format(borderForDisplayAllInteraction);
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
                            System.out.format(borderForDisplayAllInteraction);
                            break;
                        }
                        case "2": {
                            System.out.println("which id are you looking for?");
                            String inputIdInteraction = sc.nextLine();
                            System.out.println(interactionDatabase.getRow("inter_" + inputIdInteraction));
                            break;
                        }
                        case "3": {
                            System.out.println("");
                            break;
                        }
                    }
                    break;
                }
                case "3": {
                    shouldExit = true;
                    break;
                }
            }
        }
    }
}
