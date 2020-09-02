package menu;

import com.company.Database;
import com.company.DateParser;
import com.company.Interaction;
import com.company.Lead;

import java.io.IOException;
import java.security.PrivateKey;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.PrimitiveIterator;

public class MainMenu {
    private static final Database leadDatabase = new Database(Lead.fileName);
    private static final Database interactionDatabase = new Database(Interaction.fileName);

    private static String[] getLeads() {
        return leadDatabase.getAll();
    }

    private static String[] getInteractions() {
        return interactionDatabase.getAll();
    }

    public static void main(String[] args) {
        startMainMenu();
    }

    private static void startMainMenu() {
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
        }));
        optionMenu.add(new Option("Add a lead", "2", () -> {
            addLead();
        }));
        optionMenu.add(new Option("Update a lead", "3", () -> {
            updateLead();
        }));
        optionMenu.add(new Option("Delete a lead", "4", () -> {
            deleteLead();
        }));
        optionMenu.add(new Option("Back", "5", () -> {
        }));
        optionMenu.start();
        // Always go back to main menu after finishing a task
        startMainMenu();
    }

    private static void startInteractionMenu() {
        OptionMenu optionMenu = new OptionMenu();
        optionMenu.add(new Option("View all interactions", "1", () -> {
            viewInteractions();
        }));
        optionMenu.add(new Option("Add an interaction", "2", () -> {
            addInteraction();
        }));
        optionMenu.add(new Option("Update an interaction", "3", () -> {
            updateInteraction();
        }));
        optionMenu.add(new Option("Delete an interaction", "4", () -> {
            deleteInteraction();
        }));
        optionMenu.add(new Option("Back", "5", () -> {
        }));
        optionMenu.start();
        // Always go back to main menu after finishing a task
        startMainMenu();
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
        if (leadDatabase.add(lead)){
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
        String leadId = new InputField("Lead ID: ", "Lead ID does not exist.").next();
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
        if (interactionDatabase.add(interaction)){
            System.out.println("Interaction added successfully with id " + id);
            return;
        }
        System.out.println("Error occurred when adding an interaction.");
    }

    private static void updateLead() {

    }

    private static void updateInteraction() {

    }

    private static void deleteLead() {
        String id = new InputField("Enter a lead ID to delete: ", "").next();
        if(leadDatabase.delete(id)){
            System.out.println("Delete " + id + " successfully.");
            return;
        }
        System.out.println("Error occurred when deleting a lead.");


    }

    private static void deleteInteraction() {

        String id = new InputField("Enter an interaction ID to delete: ", "").next();
        if(interactionDatabase.delete(id)){
            System.out.println("Delete " + id + " successfully.");
            return;
        }
        System.out.println("Error occurred when deleting a lead.");

    }
}
