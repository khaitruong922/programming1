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

    private static String[] getLeads() {
        return leadDatabase.getAll();
    }

    private static String[] getInteractions() {
        return interactionDatabase.getAll();
    }

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
        String[] leads = getLeads();
        ArrayList<String[]> rows = new ArrayList<>();
        for (String lead : leads) {
            rows.add(Lead.fromCSV(lead).toStringArray());
        }
        TableFormatter tableFormatter = new TableFormatter(Lead.fields, rows.toArray(new String[rows.size()][Lead.fields.length]));
        tableFormatter.display();
    }

    private static void viewInteractions() {
        String[] interactions = getInteractions();
        ArrayList<String[]> rows = new ArrayList<>();
        for (String interaction : interactions) {
            rows.add(Interaction.fromCSV(interaction).toStringArray());
        }
        TableFormatter tableFormatter = new TableFormatter(Interaction.fields, rows.toArray(new String[rows.size()][Interaction.fields.length]));
        tableFormatter.display();
    }

    private static void addLead() {
        String id = Lead.idPrefix + leadDatabase.getNextIdNumber();

        String name = new InputField("Name: ", "Please type in a name").next(new RequiredValidator());
        String birthDateInput = new InputField("Birth Date (YYYY-MM-DD): ", "Invalid date format.").next(new DateValidator());
        Date birthDate = null;
        try {
            birthDate = DateParser.parse(birthDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String genderInput = new InputField("Gender (0: female, 1: male) : ", "Invalid input. Please type 0 or 1.").next(s -> s.equals("0") || s.equals("1"));
        boolean isMale = genderInput.equals("1");
        String phone = new InputField("Phone: ", "Please type in a phone number.").next(new RequiredValidator());
        String email = new InputField("Email: ", "Please type in an email.").next(new RequiredValidator());
        String address = new InputField("Address: ", "Please type in an address.").next(new RequiredValidator());
        Lead lead = new Lead(id, name, birthDate, isMale, phone, email, address);
        if (leadDatabase.add(lead)) {
            System.out.println("Lead added successfully with id " + id);
            return;
        }
        System.out.println("Error occurred when adding a lead.");


    }

    private static void addInteraction() {
        String id = Interaction.idPrefix + interactionDatabase.getNextIdNumber();
        String interDateInput = new InputField("Interaction Date (YYYY-MM-DD): ", "Invalid date format.").next(new DateValidator());
        Date interDate = null;
        try {
            interDate = DateParser.parse(interDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String leadId = new InputField("Lead ID: ", "Lead ID does not exist.").next(s -> leadDatabase.hasId(s));
        String mean = new InputField("Mean: ", "Please type in a mean.").next(new RequiredValidator());
        String potential = new InputField("Reaction (0: negative, 1: neutral, 2: positive) : ", "Invalid input. Please type in 0 or 1 or 2.")
                .next(s -> s.equals("0") || s.equals("1") || s.equals("2"));
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
        String id = new InputField("Enter a lead ID to update: ", "").next();
        if (!leadDatabase.hasId(id)) {
            System.out.println(id + " does not exist.");
            return;
        }
        String row = leadDatabase.getRow(id);
        Lead lead = Lead.fromCSV(row);
        System.out.println("Here is the data of " + id + ". Please leave the field blank and press Enter to keep the original data.");
        TableFormatter tableFormatter = new TableFormatter(Lead.fields, new String[][]{lead.toStringArray()});
        tableFormatter.display();
        String name = new InputField("Name: ").next();
        name = !name.isEmpty() ? name : lead.getName();
        String birthDateInput = new InputField("Birth Date (YYYY-MM-DD): ", "Invalid date format.").next(new DateValidator(false));
        Date birthDate = lead.getBirthDate();
        try {
            birthDate = DateParser.parse(birthDateInput);
        } catch (ParseException e) {
        }
        String genderInput = new InputField("Gender (0: female, 1: male) : ", "Invalid input. Please type 0 or 1 or press enter to skip.").next(s -> s.equals("0") || s.equals("1") || s.isEmpty());
        boolean isMale = lead.isMale();
        if (!genderInput.isEmpty()) {
            isMale = genderInput.equals("1");
        }
        String phone = new InputField("Phone: ").next();
        phone = !phone.isEmpty() ? phone : lead.getPhone();
        String email = new InputField("Email: ").next();
        email = !email.isEmpty() ? email : lead.getEmail();
        String address = new InputField("Address: ").next();
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
        String id = new InputField("Enter a inter ID to update: ", "").next();
        if (!interactionDatabase.hasId(id)) {
            System.out.println(id + " does not exist.");
            return;
        }
        String row = interactionDatabase.getRow(id);
        Interaction interaction = Interaction.fromCSV(row);
        System.out.println("Here is the data of " + id + ". Please leave the field blank and press Enter to keep the original data.");
        TableFormatter tableFormatter = new TableFormatter(Interaction.fields, new String[][]{interaction.toStringArray()});
        tableFormatter.display();
        String interactionDateInput = new InputField("Interaction Date (YYYY-MM-DD): ", "Invalid date format.").next(new DateValidator(false));
        Date interactionDate = interaction.getInteractionDate();
        try {
            interactionDate = DateParser.parse(interactionDateInput);
        } catch (ParseException e) {
        }
        String leadId = new InputField("Enter lead ID: ", "Lead ID does not exist").next(s -> leadDatabase.hasId(s) || s.isEmpty());
        leadId = !leadId.isEmpty() ? leadId : interaction.getLeadId();
        String mean = new InputField("Mean: ").next();
        mean = !mean.isEmpty() ? mean : interaction.getMean();
        String potential = new InputField("Reaction (0: negative, 1: neutral, 2: positive), enter to skip : ", "Invalid input. Please type in 0 or 1 or 2 or just enter to skip.")
                .next(s -> s.equals("0") || s.equals("1") || s.equals("2") || s.equals(""));
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
        String id = new InputField("Enter a lead ID to delete: ", "").next();
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
        String id = new InputField("Enter an interaction ID to delete: ", "").next();
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
