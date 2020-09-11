package menu;

public class MainMenu {
    private static final MainMenu instance = new MainMenu();

    private MainMenu() {

    }

    public static MainMenu getInstance() {
        return instance;
    }

    public void startMainMenu() {
        OptionMenu optionMenu = new OptionMenu();
        optionMenu.add(new Option("Lead Menu", "1", () -> {
            LeadMenu.getInstance().startLeadMenu();
        }));
        optionMenu.add(new Option("Interaction Menu", "2", () -> {
            InteractionMenu.getInstance().startInteractionMenu();
        }));
        optionMenu.add(new Option("Exit", "3", () -> {
            System.out.println("Program exit.");
        }));
        optionMenu.start();
    }
}
