package menu;

import java.util.ArrayList;
import java.util.Scanner;

public class OptionMenu {
    private final ArrayList<Option> options = new ArrayList<>();

    private static String[] fields = new String[]{"Input", "Operation"};

    public void add(Option option) {
        options.add(option);
    }

    public OptionMenu() {

    }

    private void displayOptions() {
        TableFormatter tableFormatter = new TableFormatter(fields);
        for (Option option : options) {
            tableFormatter.addRow(new String[]{option.getToggleKey(), option.getLabel()});
        }
        tableFormatter.display();
        System.out.print("Enter an option: ");
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            displayOptions();
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
