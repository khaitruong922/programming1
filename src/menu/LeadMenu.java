package menu;

import database.Database;
import database.Lead;
import util.DateParser;
import validator.DateValidator;
import validator.NameValidator;
import validator.PhoneValidator;

import java.text.ParseException;
import java.util.Date;

public class LeadMenu {
    private final Database leadDatabase = new Database(Lead.fileName);

    public void startLeadMenu() {
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
        optionMenu.add(new Option("View leads by age", "5", () -> {
            viewLeadsByAge();
            startLeadMenu();
        }));
        optionMenu.add(new Option("Back", "6", () -> {
            MainMenu mainMenu = new MainMenu();
            mainMenu.startMainMenu();
        }));
        optionMenu.start();
    }

    private void viewLeads() {
        String[] leads = leadDatabase.getAll();
        TableFormatter tableFormatter = new TableFormatter(Lead.fields);
        for (String lead : leads) {
            tableFormatter.addRow(Lead.fromCSV(lead).toStringArray());
        }
        tableFormatter.display();
    }

    private void addLead() {
        String id = Lead.idPrefix + leadDatabase.getNextIdNumber();

        String name = new InputField("Name: ").next(new NameValidator(), "Name can only contain characters and spaces only.");
        String birthDateInput = new InputField("Birth Date (YYYY-MM-DD): ").next(new DateValidator(), "Invalid date format.");
        Date birthDate = null;
        try {
            birthDate = DateParser.parse(birthDateInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String genderInput = new InputField("Gender (0: female, 1: male) : ").next(s -> s.equals("0") || s.equals("1"), "Please type in 0 or 1");
        boolean isMale = genderInput.equals("1");
        String phone = new InputField("Phone (7-12 digits): ").next(new PhoneValidator(), "Phone can only contain 7 to 12 digits only.");
        String email = new InputField("Email: ").next();
        String address = new InputField("Address: ").next();
        Lead lead = new Lead(id, name, birthDate, isMale, phone, email, address);
        if (leadDatabase.add(lead)) {
            System.out.println("Lead added successfully with id " + id);
            return;
        }
        System.out.println("Error occurred when adding a lead.");

    }

    private void updateLead() {
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

        String name = new InputField("Name: ", false)
                .next(new NameValidator(), "Name can only contain characters and spaces only.");
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

        String phone = new InputField("Phone: ", false)
                .next(new PhoneValidator(), "Phone can only contain 7 to 12 digits only.");
        phone = !phone.isEmpty() ? phone : lead.getPhone();

        String email = new InputField("Email: ", false).next();
        email = !email.isEmpty() ? email : lead.getEmail();

        String address = new InputField("Address: ", false).next();
        address = !address.isEmpty() ? address : lead.getAddress();

        Lead updatedLead = new Lead(id, name, birthDate, isMale, phone, email, address);
        if (leadDatabase.update(id, updatedLead)) {
            System.out.println("Update " + id + " successfully");
            return;
        }

        System.out.println("Error occurred when updating a lead.");
    }

    private void deleteLead() {
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

    private void viewLeadsByAge() {
        String[] leads = leadDatabase.getAll();
        String[] labels = new String[]{"0-10", "10-20", "20-60", "60+"};
        TableFormatter tableFormatter = new TableFormatter(labels);
        for (String lead : leads) {
            tableFormatter.addRow(Lead.fromCSV(lead).toStringArray());
        }
        tableFormatter.display();
    }
}
