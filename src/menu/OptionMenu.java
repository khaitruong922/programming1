package menu;


import java.util.ArrayList;
import java.util.Scanner;

public class OptionMenu {
    private ArrayList<Option> options = new ArrayList<>();

    public void addOption(Option option) {
        options.add(option);
    }

    public OptionMenu() {

    }

    private void displayOptions() {
        for (Option option : options) {
            System.out.println(option.getTitle());
        }
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
