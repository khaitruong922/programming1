package menu;

import database.Database;
import database.Interaction;
import database.Lead;
import util.DateParser;
import validator.DateValidator;
import validator.NoCommaValidator;

import java.awt.geom.FlatteningPathIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class InteractionMenu {
    private static final InteractionMenu instance = new InteractionMenu();

    public static InteractionMenu getInstance() {
        return instance;
    }

    private final Database interactionDatabase = new Database(Interaction.fileName);
    private final Database leadDatabase = new Database(Lead.fileName);
    private final OptionMenu optionMenu;

    private InteractionMenu() {
        optionMenu = new OptionMenu();
        optionMenu.add(new Option("View all interactions", "1", () -> {
            viewInteractions();
            waitForEnter();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("Add an interaction", "2", () -> {
            addInteraction();
            waitForEnter();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("Update an interaction", "3", () -> {
            updateInteraction();
            waitForEnter();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("Delete an interaction", "4", () -> {
            deleteInteraction();
            waitForEnter();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("View interactions by potential", "5", () -> {
            viewInteractionsByPotential();
            waitForEnter();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("View interactions by month", "6", () -> {
            viewInteractionsByMonth();
            waitForEnter();
            startInteractionMenu();
        }));
        optionMenu.add(new Option("Back", "7", () -> {
            MainMenu.getInstance().startMainMenu();
        }));
    }

    public void startInteractionMenu() {
        optionMenu.start();
    }

    private void viewInteractions() {
        String[] rows = interactionDatabase.getAll();
        TableFormatter tableFormatter = new TableFormatter(Interaction.fields);
        for (String row : rows) {
            tableFormatter.addRow(Interaction.fromCSV(row).toStringArray());
        }
        tableFormatter.display();
    }

    private void addInteraction() {
        String id = Interaction.idPrefix + interactionDatabase.getNextIdNumber();
        Date interactionDate = askInteractionDate(true);
        String leadId = askLeadId(true);
        String mean = askMean(true);
        String potential = askPotential(true);
        Interaction interaction = new Interaction(id, interactionDate, leadId, mean, potential);

        if (interactionDatabase.add(interaction)) {
            System.out.println("Interaction added successfully with id " + id);
            TableFormatter tableFormatter = new TableFormatter(Interaction.fields);
            tableFormatter.addRow(interaction.toStringArray());
            tableFormatter.display();
            return;
        }
        System.out.println("Error occurred when adding an interaction.");
    }

    private void updateInteraction() {
        String id = new InputField("Enter an interaction ID to update (leave blank to cancel): ", false).next();
        if (id.isEmpty()) return;
        if (!interactionDatabase.hasId(id)) {
            System.out.println(id + " does not exist.");
            return;
        }

        String row = interactionDatabase.getRow(id);
        Interaction interaction = Interaction.fromCSV(row);

        System.out.println("Below is the data of " + id + ". Please leave the field blank and press Enter to keep the original data.");
        TableFormatter tableFormatter = new TableFormatter(Interaction.fields);
        tableFormatter.addRow(interaction.toStringArray());
        tableFormatter.display();

        Date interactionDate = askInteractionDate(false);
        if (interactionDate != null) interaction.setInteractionDate(interactionDate);

        String leadId = askLeadId(false);
        if (!leadId.isEmpty()) interaction.setLeadId(leadId);

        String mean = askMean(false);
        if (!mean.isEmpty()) interaction.setMean(mean);

        String potential = askPotential(false);
        if (!potential.isEmpty()) interaction.setPotential(potential);

        if (interactionDatabase.update(id, interaction)) {
            System.out.println("Update " + id + " successfully.");
            TableFormatter updatedTableFormatter = new TableFormatter(Interaction.fields);
            updatedTableFormatter.addRow(interaction.toStringArray());
            updatedTableFormatter.display();
            return;
        }

        System.out.println("Error occurred when updating a lead.");
    }

    private void deleteInteraction() {
        String id = new InputField("Enter an interaction ID to delete (leave blank to cancel): ", false).next();
        if (id.isEmpty()) return;
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
        Date startDate = askStartDate();
        Date endDate = askEndDate(startDate);
        HashMap<String, Integer> potentialCounter = new HashMap<>();
        potentialCounter.put("negative", 0);
        potentialCounter.put("neutral", 0);
        potentialCounter.put("positive", 0);
        String[] rows = interactionDatabase.getAll();
        for (String row : rows) {
            Interaction interaction = Interaction.fromCSV(row);
            Date interactionDate = interaction.getInteractionDate();
            // Skip if the interaction date is not between start and end date.
            if (interactionDate.getTime() < startDate.getTime()) continue;
            if (interactionDate.getTime() > endDate.getTime()) continue;
            String potential = interaction.getPotential();
            if (potentialCounter.containsKey(potential)) {
                potentialCounter.put(potential, potentialCounter.get(potential) + 1);
            }
        }
        TableFormatter tableFormatter = new TableFormatter(new String[]{"Potential", "Interactions"});
        tableFormatter.addRow(new String[]{"Negative", Integer.toString(potentialCounter.get("negative"))});
        tableFormatter.addRow(new String[]{"Neutral", Integer.toString(potentialCounter.get("neutral"))});
        tableFormatter.addRow(new String[]{"Positive", Integer.toString(potentialCounter.get("positive"))});
        tableFormatter.display();
    }

    private void viewInteractionsByMonth() {
        Date startDate = askStartDate();
        Date endDate = askEndDate(startDate);
        String[] months = getMonthsBetweenTwoDates(startDate, endDate);
        HashMap<String, Integer> interactionCounter = new HashMap<>();
        for (String month : months) {
            interactionCounter.put(month, 0);
        }
        String[] rows = interactionDatabase.getAll();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM yyyy");
        for (String row : rows) {
            Interaction interaction = Interaction.fromCSV(row);
            Date interactionDate = interaction.getInteractionDate();
            // Skip if the interaction date is not between start and end date.
            if (interactionDate.getTime() < startDate.getTime()) continue;
            if (interactionDate.getTime() > endDate.getTime()) continue;

            String month = simpleDateFormat.format(interaction.getInteractionDate());
            if (interactionCounter.containsKey(month)) {
                interactionCounter.put(month, interactionCounter.get(month) + 1);
            }
        }
        TableFormatter tableFormatter = new TableFormatter(new String[]{"Month", "Interactions"});
        for (String month : months) {
            tableFormatter.addRow(new String[]{month, Integer.toString(interactionCounter.get(month))});
        }
        tableFormatter.display();
    }

    private String[] getMonthsBetweenTwoDates(Date start, Date end) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(start);
        Calendar cEnd = Calendar.getInstance();
        cEnd.setTime(end);
        // Add 1 day to end date to include the end date in the calculation
        cEnd.add(Calendar.DATE, 1);
        ArrayList<String> months = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM yyyy");
        while (cStart.before(cEnd)) {
            months.add(simpleDateFormat.format(cStart.getTime()));
            cStart.add(Calendar.MONTH, 1);
            cStart.set(Calendar.DAY_OF_MONTH, 1);
        }
        return months.toArray(new String[months.size()]);
    }

    private Date askInteractionDate(boolean required) {
        String interactionDateInput = new InputField("Interaction Date (YYYY-MM-DD): ", required)
                .next(new DateValidator(), "");

        // This only runs when field is not required
        if (interactionDateInput.isEmpty()) return null;

        Date interactionDate = null;
        try {
            interactionDate = DateParser.parse(interactionDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interactionDate;
    }

    private String askLeadId(boolean required) {
        return new InputField("Enter lead ID: ", required)
                .next(s -> leadDatabase.hasId(s),
                        "Lead ID does not exist");
    }

    private String askMean(boolean required) {
        return new InputField("Mean: ", required).next(new NoCommaValidator(), "Comma is not allowed.");
    }

    private String askPotential(boolean required) {
        String potentialInput = new InputField("Potential (0: negative, 1: neutral, 2: positive): ", required)
                .next(s -> s.equals("0") || s.equals("1") || s.equals("2"));
        return inputToPotential(potentialInput);
    }

    private String inputToPotential(String input) {
        switch (input) {
            case "0":
                return "negative";
            case "1":
                return "neutral";
            case "2":
                return "positive";
        }
        return "";
    }

    private Date askStartDate() {
        String startDateInput = new InputField("Enter start date (yyyy-mm-dd): ").next(new DateValidator(), "");
        Date startDate = null;
        try {
            startDate = DateParser.parse(startDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    private Date askEndDate(Date startDate) {
        Date endDate = null;
        do {
            String endDateInput = new InputField("Enter end date (yyyy-mm-dd): ").next(new DateValidator(), "");
            try {
                endDate = DateParser.parse(endDateInput);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (endDate.getTime() < startDate.getTime()) {
                System.out.println("End date should be after start date.");
            }
        }
        while (endDate.getTime() < startDate.getTime());
        return endDate;
    }

    private void waitForEnter() {
        new InputField("Press Enter to continue. ", false).next();
    }
}
