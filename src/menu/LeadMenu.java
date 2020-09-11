package menu;

import database.Database;
import database.Interaction;
import database.Lead;
import util.DateParser;
import validator.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LeadMenu {
    private final Database leadDatabase = new Database(Lead.fileName);
    private final Database interactionDatabase = new Database(Interaction.fileName);
    private static final LeadMenu instance = new LeadMenu();

    private LeadMenu() {

    }

    public static LeadMenu getInstance() {
        return instance;
    }

    public void startLeadMenu() {
        OptionMenu optionMenu = new OptionMenu();
        optionMenu.add(new Option("View all leads", "1", () -> {
            viewLeads();
            waitForEnter();
            startLeadMenu();
        }));
        optionMenu.add(new Option("Add a lead", "2", () -> {
            addLead();
            waitForEnter();
            startLeadMenu();
        }));
        optionMenu.add(new Option("Update a lead", "3", () -> {
            updateLead();
            waitForEnter();
            startLeadMenu();
        }));
        optionMenu.add(new Option("Delete a lead", "4", () -> {
            deleteLead();
            waitForEnter();
            startLeadMenu();
        }));
        optionMenu.add(new Option("View leads by age", "5", () -> {
            viewLeadsByAge();
            waitForEnter();
            startLeadMenu();
        }));
        optionMenu.add(new Option("Back", "6", () -> {
            MainMenu.getInstance().startMainMenu();
        }));
        optionMenu.start();
    }

    private void viewLeads() {
        String[] rows = leadDatabase.getAll();
        TableFormatter tableFormatter = new TableFormatter(Lead.fields);
        for (String row : rows) {
            tableFormatter.addRow(Lead.fromCSV(row).toStringArray());
        }
        tableFormatter.display();
    }

    private void addLead() {
        String id = Lead.idPrefix + leadDatabase.getNextIdNumber();

        String name = new InputField("Name: ").next(new NameValidator(), "Name can only contain characters and spaces only.");
        String birthDateInput = new InputField("Birth Date (YYYY-MM-DD): ").next(new DateValidator(), "");
        Date birthDate = null;
        try {
            birthDate = DateParser.parse(birthDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String genderInput = new InputField("Gender (0: female, 1: male) : ").next(s -> s.equals("0") || s.equals("1"), "Invalid input.");
        boolean isMale = genderInput.equals("1");
        String phone = new InputField("Phone (7-12 digits): ").next(new PhoneValidator(), "Phone can only contain 7 to 12 digits only.");
        String email = new InputField("Email: ")
                .next(new EmailValidator(), "Invalid email format.");
        String address = new InputField("Address: ").next();
        Lead lead = new Lead(id, name, birthDate, isMale, phone, email, address);
        if (leadDatabase.add(lead)) {
            System.out.println("Lead added successfully with id " + id);
            TableFormatter tableFormatter = new TableFormatter(Lead.fields);
            tableFormatter.addRow(lead.toStringArray());
            tableFormatter.display();

            return;
        }
        System.out.println("Error occurred when adding a lead.");

    }

    private void updateLead() {
        String id = new InputField("Enter a lead ID to update (leave blank to cancel): ",false).next();
        if (id.isEmpty()) return;
        if (!leadDatabase.hasId(id)) {
            System.out.println(id + " does not exist.");
            return;
        }

        String row = leadDatabase.getRow(id);
        Lead lead = Lead.fromCSV(row);

        System.out.println("Below is the data of " + id + ". Please leave the field blank and press Enter to keep the original data.");
        TableFormatter tableFormatter = new TableFormatter(Lead.fields);
        tableFormatter.addRow(lead.toStringArray());
        tableFormatter.display();

        String name = new InputField("Name: ", false)
                .next(new NameValidator(), "Name can only contain characters and spaces only.");
        name = !name.isEmpty() ? name : lead.getName();
        lead.setName(name);

        String birthDateInput = new InputField("Birth Date (YYYY-MM-DD): ", false)
                .next(new DateValidator(), "");
        Date birthDate = lead.getBirthDate();
        try {
            birthDate = DateParser.parse(birthDateInput);
        } catch (ParseException e) {
        }
        lead.setBirthDate(birthDate);

        String genderInput = new InputField("Gender (0: female, 1: male) : ", false)
                .next(s -> s.equals("0") || s.equals("1") || s.isEmpty());
        boolean isMale = lead.isMale();
        if (!genderInput.isEmpty()) {
            isMale = genderInput.equals("1");
        }
        lead.setMale(isMale);

        String phone = new InputField("Phone: ", false)
                .next(new PhoneValidator(), "Phone can only contain 7 to 12 digits only.");
        phone = !phone.isEmpty() ? phone : lead.getPhone();
        lead.setPhone(phone);

        String email = new InputField("Email: ", false)
                .next(new EmailValidator(), "Invalid email format.");
        email = !email.isEmpty() ? email : lead.getEmail();
        lead.setEmail(email);

        String address = new InputField("Address: ", false).next();
        address = !address.isEmpty() ? address : lead.getAddress();
        lead.setAddress(address);

        if (leadDatabase.update(id, lead)) {
            System.out.println("Update " + id + " successfully.");
            TableFormatter updatedTableFormatter = new TableFormatter(Lead.fields);
            updatedTableFormatter.addRow(lead.toStringArray());
            updatedTableFormatter.display();
            return;
        }

        System.out.println("Error occurred when updating a lead.");
    }

    private void deleteLead() {
        String id = new InputField("Enter a lead ID to delete (leave blank to cancel): ", false).next();
        if (id.isEmpty()) return;
        if (!leadDatabase.hasId(id)) {
            System.out.println(id + " does not exist.");
            return;
        }
        // Check if lead involves in interaction
        String[] interactionLeads = interactionDatabase.getColumn(2);
        int leadInteractionCount = 0;
        for (String interactionLead : interactionLeads) {
            if (interactionLead.equals(id)) {
                leadInteractionCount++;
            }
        }
        if (leadInteractionCount > 0) {
            System.out.println(id + " is involved in " + leadInteractionCount + " interaction(s). Delete this lead will also delete interactions that involve this lead.");
            String input = new InputField("Type y to confirm deletion, n to cancel: ").next(s -> s.equals("y") || s.equals("n"));
            if (input.equals("n")) return;
        }

        if (leadDatabase.delete(id)) {
            System.out.println("Delete " + id + " successfully.");
            interactionDatabase.deleteMatch(id, 2);
            return;
        }
        System.out.println("Error occurred when deleting a lead.");
    }

    private void viewLeadsByAge() {
        String[] rows = leadDatabase.getAll();
        String[] labels = new String[]{"0-10", "10-20", "20-60", "60+"};
        int[] leadCounts = new int[labels.length];
        for (String row : rows
        ) {
            Lead lead = Lead.fromCSV(row);
            Calendar c = Calendar.getInstance();
            c.setTime(lead.getBirthDate());
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int date = c.get(Calendar.DATE);
            Period diff = Period.between(LocalDate.of(year, month + 1, date), LocalDate.now());
            int age = diff.getYears();
            // Classify into groups
            if (age < 10) {
                leadCounts[0] += 1;
                continue;
            }
            if (age < 20) {
                leadCounts[1] += 1;
                continue;
            }
            if (age < 60) {
                leadCounts[2] += 1;
                continue;
            }
            // Only runs when lead age is over 60
            leadCounts[3] += 1;
        }
        String[] strLeadCounts = new String[leadCounts.length];
        for (int i = 0; i < leadCounts.length; i++) {
            strLeadCounts[i] = Integer.toString(leadCounts[i]);
        }
        TableFormatter tableFormatter = new TableFormatter(labels);
        tableFormatter.addRow(strLeadCounts);
        tableFormatter.display();
    }

    private void waitForEnter() {
        new InputField("Press Enter to continue. ", false).next();
    }
}
