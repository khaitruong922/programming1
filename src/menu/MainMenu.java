package menu;

public class MainMenu {
    public static void main(String[] args) {
        OptionMenu optionMenu = new OptionMenu();
        Option leadOption = new Option("Lead", "1", () -> {

        });
        Option interactionOption = new Option("Interaction", "2", () -> {

        });
        Option exitOption = new Option("Exit", "3", () -> {

        }
        );
        optionMenu.add(leadOption);
        optionMenu.add(interactionOption);
        optionMenu.add(exitOption);
        optionMenu.start();
    }
}
