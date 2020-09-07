package menu;

import database.Database;
import util.DateParser;
import database.Interaction;
import database.Lead;
import validator.DateValidator;
import validator.RequiredValidator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class MainMenu {
    private static final Database leadDatabase = new Database(Lead.fileName);
    private static final Database interactionDatabase = new Database(Interaction.fileName);

    public static void startMainMenu() {
        OptionMenu optionMenu = new OptionMenu();
        optionMenu.add(new Option("Lead Menu", "1", () -> {
            startLeadMenu();
        }));
        optionMenu.add(new Option("Interaction Menu", "2", () -> {
            startInteractionMenu();
        }));
        optionMenu.add(new Option("Exit", "3", () -> {
            System.out.println("Program exit");
        }));

        optionMenu.start();
    }

    private static void startLeadMenu() {
        OptionMenu optionMenu = new OptionMenu();
        optionMenu.add(new Option("View all leads", "1", () -> {
            viewLeads();
            startLeadMenu();
        }));
        optionMenu.add(new Option("Add a lead", "2", () -> {
            addLead();
            startLeadMenu();
        }));
        optionMenu.add(new Option("Update a lead", "3", () -> {
            updateLead();
            startLeadMenu();
        }));
        optionMenu.add(new Option("Delete a lead", "4", () -> {
            deleteLead();
            startLeadMenu();
        }));
        optionMenu.add(new Option("Back", "5", () -> {
            startMainMenu();
        }));
        optionMenu.start();
    }

    private static void startInteractionMenu() {
        OptionMenu optionMenu = new OptionMenu();
        optionMenu.add(new Option("View all interactions", "1", () -> {
            viewInteractions();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("Add an interaction", "2", () -> {
            addInteraction();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("Update an interaction", "3", () -> {
            updateInteraction();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("Delete an interaction", "4", () -> {
            deleteInteraction();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("Back", "5", () -> {
            startMainMenu();

        }));
        optionMenu.start();
    }

    private static void viewLeads() {
        String[] leads = leadDatabase.getAll();
        TableFormatter tableFormatter = new TableFormatter(Lead.fields);
        for (String lead : leads) {
            tableFormatter.addRow(Lead.fromCSV(lead).toStringArray());
        }
        tableFormatter.display();
    }

    private static void viewInteractions() {
        String[] interactions = interactionDatabase.getAll();
        TableFormatter tableFormatter = new TableFormatter(Interaction.fields);
        for (String interaction : interactions) {
            tableFormatter.addRow(Interaction.fromCSV(interaction).toStringArray());
        }
        tableFormatter.display();
    }

    private static void addLead() {
        String id = Lead.idPrefix + leadDatabase.getNextIdNumber();

        String name = new InputField("Name: ").next();
        String birthDateInput = new InputField("Birth Date (YYYY-MM-DD): ").next(new DateValidator(), "Invalid date format.");
        Date birthDate = null;
        try {
            birthDate = DateParser.parse(birthDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String genderInput = new InputField("Gender (0: female, 1: male) : ").next(s -> s.equals("0") || s.equals("1"), "Please type in 0 or 1");
        boolean isMale = genderInput.equals("1");
        String phone = new InputField("Phone: ").next();
        String email = new InputField("Email: ").next();
        String address = new InputField("Address: ").next();
        Lead lead = new Lead(id, name, birthDate, isMale, phone, email, address);
        if (leadDatabase.add(lead)) {
            System.out.println("Lead added successfully with id " + id);
            return;
        }
        System.out.println("Error occurred when adding a lead.");


    }

    private static void addInteraction() {
        String id = Interaction.idPrefix + interactionDatabase.getNextIdNumber();
        String interDateInput = new InputField("Interaction Date (YYYY-MM-DD): ").next(new DateValidator(), "Invalid date format.");
        Date interDate = null;
        try {
            interDate = DateParser.parse(interDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String leadId = new InputField("Lead ID: ").next(s -> leadDatabase.hasId(s), "Lead id does not exist.");
        String mean = new InputField("Mean: ").next();
        String potential = new InputField("Reaction (0: negative, 1: neutral, 2: positive) : ")
                .next(s -> s.equals("0") || s.equals("1") || s.equals("2"),
                        "Invalid input. Please type in 0 or 1 or 2.");
        switch (potential) {
            case "0": {
                potential = "negative";
                break;
            }
            case "1": {
                potential = "neutral";
                break;
            }
            case "2": {
                potential = "positive";
                break;
            }
        }
        Interaction interaction = new Interaction(id, interDate, leadId, mean, potential);
        if (interactionDatabase.add(interaction)) {
            System.out.println("Interaction added successfully with id " + id);
            return;
        }
        System.out.println("Error occurred when adding an interaction.");
    }

    private static void updateLead() {
        String id = new InputField("Enter a lead ID to update: ").next();
        if (!leadDatabase.hasId(id)) {
            System.out.println(id + " does not exist.");
            return;
        }
        String row = leadDatabase.getRow(id);
        Lead lead = Lead.fromCSV(row);
        System.out.println("Here is the data of " + id + ". Please leave the field blank and press Enter to keep the original data.");
        TableFormatter tableFormatter = new TableFormatter(Lead.fields);
        tableFormatter.addRow(lead.toStringArray());
        tableFormatter.display();
        String name = new InputField("Name: ", false).next();
        name = !name.isEmpty() ? name : lead.getName();
        String birthDateInput = new InputField("Birth Date (YYYY-MM-DD): ", false)
                .next(new DateValidator(), "Invalid date format.");
        Date birthDate = lead.getBirthDate();
        try {
            birthDate = DateParser.parse(birthDateInput);
        } catch (ParseException e) {
        }
        String genderInput = new InputField("Gender (0: female, 1: male) : ", false)
                .next(s -> s.equals("0") || s.equals("1") || s.isEmpty(),
                        "Invalid input. Please type 0 or 1 or press enter to skip.");
        boolean isMale = lead.isMale();
        if (!genderInput.isEmpty()) {
            isMale = genderInput.equals("1");
        }
        String phone = new InputField("Phone: ", false).next();
        phone = !phone.isEmpty() ? phone : lead.getPhone();
        String email = new InputField("Email: ", false).next();
        email = !email.isEmpty() ? email : lead.getEmail();
        String address = new InputField("Address: ", false).next();
        address = !address.isEmpty() ? address : lead.getAddress();
        Lead updatedLead = new Lead(id, name, birthDate, isMale, phone, email, address);
        // update lead in CSV file
        if (leadDatabase.update(id, updatedLead)) {
            System.out.println("Update " + id + " successfully");
            return;
        }
        System.out.println("Error occurred when updating a lead.");
    }

    private static void updateInteraction() {
        String id = new InputField("Enter a inter ID to update: ").next();
        if (!interactionDatabase.hasId(id)) {
            System.out.println(id + " does not exist.");
            return;
        }
        String row = interactionDatabase.getRow(id);
        Interaction interaction = Interaction.fromCSV(row);
        System.out.println("Here is the data of " + id + ". Please leave the field blank and press Enter to keep the original data.");
        TableFormatter tableFormatter = new TableFormatter(Interaction.fields);
        tableFormatter.addRow(interaction.toStringArray());
        tableFormatter.display();
        String interactionDateInput = new InputField("Interaction Date (YYYY-MM-DD): ", false)
                .next(new DateValidator(), "Invalid date format");
        Date interactionDate = interaction.getInteractionDate();
        try {
            interactionDate = DateParser.parse(interactionDateInput);
        } catch (ParseException e) {
        }
        String leadId = new InputField("Enter lead ID: ", false)
                .next(s -> leadDatabase.hasId(s),
                        "Lead ID does not exist");
        leadId = !leadId.isEmpty() ? leadId : interaction.getLeadId();
        String mean = new InputField("Mean: ", false).next();
        mean = !mean.isEmpty() ? mean : interaction.getMean();
        String potential = new InputField("Reaction (0: negative, 1: neutral, 2: positive), enter to skip : ", false)
                .next(s -> s.equals("0") || s.equals("1") || s.equals("2"),
                        "Invalid input. Please type in 0 or 1 or 2 or enter to skip.");
        switch (potential) {
            case "0": {
                potential = "negative";
                break;
            }
            case "1": {
                potential = "neutral";
                break;
            }
            case "2": {
                potential = "positive";
                break;
            }
            default: {
                potential = interaction.getPotential();
                break;
            }
        }
        Interaction updatedInteraction = new Interaction(interaction.getId(), interactionDate, leadId, mean, potential);
        //print update interaction
        if (interactionDatabase.update(id, updatedInteraction)) {
            System.out.println("Update " + id + " successfully.");
            return;
        }
        System.out.println("Error occurred when updating a lead.");
    }

    private static void deleteLead() {
        String id = new InputField("Enter a lead ID to delete: ").next();
        if (!leadDatabase.hasId(id)) {
            System.out.println(id + " does not exist.");
            return;
        }
        if (leadDatabase.delete(id)) {
            System.out.println("Delete " + id + " successfully.");
            return;
        }
        System.out.println("Error occurred when deleting a lead.");


    }

    private static void deleteInteraction() {
        String id = new InputField("Enter an interaction ID to delete: ").next();
        if (!interactionDatabase.hasId(id)) {
            System.out.println(id + " does not exist.");
            return;
        }
        if (interactionDatabase.delete(id)) {
            System.out.println("Delete " + id + " successfully.");
            return;
        }
        System.out.println("Error occurred when deleting a lead.");

    }
}
