package com.company;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class Menu {
    private static Database leadDatabase = new Database(Lead.fileName);
    private static Database interactionDatabase = new Database(Interaction.fileName);

    private static String[] getLeads() {
        return leadDatabase.getAll();
    }

    private static String[] getInteractions() {
        return interactionDatabase.getAll();
    }

    private static String leftAlignFormat = "| %-15s | %-6s |%n";
    private static Scanner sc = new Scanner(System.in);


    public static void start() throws Exception {


        boolean shouldExit = false;
        while (!shouldExit) {
            printMenuOptions();
            String menuInput = sc.nextLine();
            switch (menuInput) {
                case "1": {
                    startLeadMenu();
                    break;
                }
                case "2": {
                    startInteractionMenu();
                    break;
                }
                case "3": {
                    shouldExit = true;
                    break;
                }
            }
        }
    }

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
        System.out.format(leftAlignFormat, "Delete", "4");
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

    private static void printLeadTable(String[] leads) {
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
        String displayFormat = "| %-" + fLeadId + "s | %-" + fName + "s | %-" + fBirthday + "s | %-6s | %-" + fPhone + "s | %-" + fEmail + "s | %-" + fAddress + "s |%n";
        String border = String.format(displayFormat, "", "", "", "", "", "", "").replace(" ", "-").replace("|", "+");
        System.out.print(border);
        System.out.format(displayFormat, "Lead ID", "Name", "Birth Date", "Gender", "Phone", "Email", "Address");
        System.out.print(border);
        for (int i = 0; i < leads.length; i++) {
            Lead lead = Lead.fromCSV(leads[i]);
            System.out.format(
                    displayFormat,
                    lead.getId(),
                    lead.getName(),
                    DateParser.dateToString(lead.getBirthDate()),
                    lead.isMale() ? "Male" : "Female",
                    lead.getPhone(),
                    lead.getEmail(),
                    lead.getAddress());
        }
        System.out.print(border);
    }

    private static void printInteractionTable(String[] interactions) {
        int fInteractionID = 0, fInteractionDate = 0, fInteractionLeadID = 0, fMean = 0; //potential = 9
        for (int i = 0; i < interactions.length; i++) {
            Interaction interaction = Interaction.fromCSV(interactions[i]);
            fInteractionID = Math.max(Math.max(fInteractionID, interaction.getId().length()), "Interaction ID".length());
            fInteractionDate = Math.max(Math.max(fInteractionDate, DateParser.dateToString(interaction.getInteractionDate()).length()), "Interaction Date".length());
            fInteractionLeadID = Math.max(fInteractionLeadID, interaction.getLeadId().length());
            fMean = Math.max(Math.max(fMean, interaction.getMean().length()), "Lead ID".length());
        }
        String displayFormat = "| %-" + fInteractionID + "s | %-" + fInteractionDate + "s | %-" + fInteractionLeadID + "s | %-" + fMean + "s | %-9s |%n";
        String border = String.format(displayFormat, "", "", "", "", "").replace(" ", "-").replace("|", "+");
        System.out.format(border);
        System.out.format(displayFormat, "Interaction ID", "Interaction Date", "Lead ID", "Mean ", "Potential");
        System.out.format(border);
        for (int i = 0; i < interactions.length; i++) {
            Interaction interaction = Interaction.fromCSV(interactions[i]);
            System.out.format(
                    displayFormat,
                    interaction.getId(),
                    DateParser.dateToString(interaction.getInteractionDate()),
                    interaction.getLeadId(),
                    interaction.getMean(),
                    interaction.getPotential());
        }
        System.out.format(border);
    }

    private static void startLeadMenu() throws IOException {
        printLeadMenuOptions();
        String input = sc.nextLine();
        switch (input) {
            case "1": {
                printLeadTable(getLeads());
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
                    System.out.println("Error occurred when trying to add a lead.");
                    e.printStackTrace();
                }
                break;
            }
            case "4": {
                System.out.println("please enter the id to delete: ");
                String deleteLead = sc.nextLine();
                leadDatabase.delete("lead_" + deleteLead);
            }
        }
    }

    private static void startInteractionMenu() throws IOException {
        printInteractionMenuOptions();
        String input = sc.nextLine();
        switch (input) {
            case "1": {
                printInteractionTable(getInteractions());
                break;
            }
            case "2": {
                System.out.println("which id are you looking for?");
                String inputIdInteraction = sc.nextLine();
                System.out.println(interactionDatabase.getRow("inter_" + inputIdInteraction));
                break;
            }
            case "3": {
                System.out.println("adding an interaction");
                Date interactionDate = null;
                do {
                    try {
                        System.out.print("Interaction date (YYYY-MM-DD)    | ");
                        String interactionDateInput = sc.nextLine();
                        interactionDate = DateParser.stringToDate(interactionDateInput);
                    } catch (ParseException e) {
                        System.out.println("Invalid format. Please try again.");
                    }
                } while (interactionDate == null);
                System.out.print("inter lead id                |");
                String interLeadIdInput = sc.nextLine();
                System.out.print("inter mean                   |");
                String mean = sc.nextLine();
                boolean valid = true;
                Interaction.Potential potential = Interaction.Potential.neutral;
                do {
                    if (!valid) {
                        System.out.println("Invalid input. Please type 0 for negative and 1 for neutral, 2 for positive.");
                    }
                    System.out.print("Potential (0: negative, 1: neutral, 2: positive)                | ");
                    String choice = sc.nextLine();
                    valid = (choice.equals("0")) || (choice.equals("1") || (choice.equals("2")));
                    switch (choice) {
                        case "0":
                            potential = Interaction.Potential.negative;
                            break;
                        case "1":
                            potential = Interaction.Potential.neutral;
                            break;
                        case "2":
                            potential = Interaction.Potential.positive;
                            break;
                    }

                } while (!valid);
                Interaction addInteraction = new Interaction(Interaction.idPrefix + interactionDatabase.getNextId(), interactionDate, Lead.idPrefix + interLeadIdInput, mean, potential);
                try {
                    interactionDatabase.add(addInteraction);
                    System.out.println("Interaction added successfully");
                } catch (IOException e) {
                    System.out.println("Error occurred when trying to add a interaction.");
                    e.printStackTrace();
                }
                break;
            }
            case "4": {
                System.out.println("please enter the id to delete: ");
                String deleteInteraction = sc.nextLine();
                interactionDatabase.delete("inter_" + deleteInteraction);
            }
        }
    }
}
