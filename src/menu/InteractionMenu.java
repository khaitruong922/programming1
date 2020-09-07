package menu;

import database.Database;
import database.Interaction;
import database.Lead;
import main.Main;
import util.DateParser;
import validator.DateValidator;

import java.text.ParseException;
import java.util.Date;

public class InteractionMenu {
    private final Database interactionDatabase = new Database(Interaction.fileName);
    private final Database leadDatabase = new Database(Lead.fileName);

    public void startInteractionMenu() {
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
        optionMenu.add(new Option("View interactions by potential", "5", () -> {
            viewInteractionsByPotential();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("View interactions by month", "6", () -> {
            viewInteractionsByMonth();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("Back", "7", () -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.startMainMenu();
        }));
        optionMenu.start();
    }


    private void viewInteractions() {
        String[] interactions = interactionDatabase.getAll();
        TableFormatter tableFormatter = new TableFormatter(Interaction.fields);
        for (String interaction : interactions) {
            tableFormatter.addRow(Interaction.fromCSV(interaction).toStringArray());
        }
        tableFormatter.display();
    }


    private void addInteraction() {
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


    private void updateInteraction() {
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
        if (interactionDatabase.update(id, updatedInteraction)) {
            System.out.println("Update " + id + " successfully.");
            return;
        }

        System.out.println("Error occurred when updating a lead.");
    }


    private void deleteInteraction() {
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


    private void viewInteractionsByPotential() {

    }

    private void viewInteractionsByMonth() {
        String startDateInput = new InputField("Enter the start date (yyyy-mm-dd): ").next(new DateValidator(), "Invalid date format.");
        String endDateInput = new InputField("Enter the end date (yyyy-mm-dd): ").next(new DateValidator(), "Invalid date format.");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateParser.parse(startDateInput);
            endDate = DateParser.parse(endDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(DateParser.format(startDate) + " and " + DateParser.format(endDate));

    }
}
