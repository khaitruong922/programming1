package menu;

import com.company.Database;
import com.company.DateParser;
import com.company.Interaction;
import com.company.Lead;

import java.io.IOException;
import java.security.PrivateKey;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.PrimitiveIterator;

public class MainMenu {
    private static final Database leadDatabase = new Database(Lead.fileName);
    private static final Database interactionDatabase = new Database(Interaction.fileName);

    private static String[] getLeads() {
        return leadDatabase.getAll();
    }

    private static int getNumberOfLeadRow() {
        return leadDatabase.getNumberOfRow();
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
            startMainMenu();
        }));
        optionMenu.start();
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
            startMainMenu();
        }));
        optionMenu.start();
    }

    private static void viewLeads() {
        String[] lead = getLeads();
        String[][] leads = new String[getNumberOfLeadRow()][];
        for (int i = 0; i < getNumberOfLeadRow(); i++) {
            leads[i] = lead;
        }
        String[] labels = new String[]{"lead ID", "Name", "Birth Day", "Gender", "Phone", "Email", "Address"};
        TableFormatter tableFormatter = new TableFormatter(labels,leads);
        tableFormatter.display();
    }

    private static void viewInteractions() {

    }

    private static void addLead() {
        String name = new InputField("Name: ").next();
        String birthDateInput = new InputField("Birth Date (YYYY-MM-DD): ", "Invalid date format.").next(new DateValidator());
        Date birthDate = null;
        try {
            birthDate = DateParser.parse(birthDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String genderInput = new InputField("Gender: ", "Invalid input").next(s -> s.equals("0") || s.equals("1"));
        boolean isMale = genderInput.equals("1");
        String phone = new InputField("Phone: ").next();
        String email = new InputField("Email: ").next();
        String address = new InputField("Address: ").next();
        Lead lead = new Lead(leadDatabase.getNextId(), name, birthDate, isMale, phone, email, address);
        try {
            leadDatabase.add(lead);
            System.out.println("Lead added successfully");
        } catch (IOException e) {
            System.out.println("Error occurred when adding a lead.");
            e.printStackTrace();
        }

    }

    private static void addInteraction() {
        String interDateInput = new InputField("Interaction Date (YYYY-MM-DD): ", "Invalid date format.").next(new DateValidator());
        Date interDate = null;
        try {
            interDate = DateParser.parse(interDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String interLeadIDInput = new InputField("Lead ID: ", "Invalid Lead ID format.").next();
        String meanInput = new InputField("Mean: ", "Invalid mean format").next();

    }

    private static void updateLead() {

    }

    private static void updateInteraction() {

    }

    private static void deleteLead() {
        String leadID = new InputField("input lead ID","Invalid lead ID format.").next();
        try {
            leadDatabase.delete(leadID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteInteraction() {

            String interactionID = new InputField("input interaction ID", "invalid interaction ID format.").next();
        try {
            interactionDatabase.delete(interactionID);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
