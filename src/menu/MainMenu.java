package menu;

import com.company.Database;
import com.company.Interaction;
import com.company.Lead;

import java.util.Arrays;

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
        String[] leads = getLeads();
        System.out.println(Arrays.toString(leads));
    }

    private static void viewInteractions() {

    }

    private static void addLead() {

    }

    private static void addInteraction() {

    }

    private static void updateLead() {

    }

    private static void updateInteraction() {

    }

    private static void deleteLead() {

    }

    private static void deleteInteraction() {

    }
}
