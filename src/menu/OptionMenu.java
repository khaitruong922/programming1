package menu;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.Scanner;

public class OptionMenu {
    private final ArrayList<Option> options = new ArrayList<>();

    public void add(Option option) {
        options.add(option);
    }

    private void displayOptions() {
        TableFormatter tableFormatter = new TableFormatter(Option.fields);
        for (Option option : options) {
            tableFormatter.addRow(new String[]{option.getToggleKey(), option.getLabel()});
        }
        tableFormatter.display();
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        displayOptions();
        while (true) {
            System.out.print("Enter an option: ");
            String input = sc.next();
            for (Option option : options) {
                if (option.getToggleKey().equals(input)) {
                    option.execute();
                    return;
                }
            }
            System.out.println("Invalid input");
        }

    }
}
