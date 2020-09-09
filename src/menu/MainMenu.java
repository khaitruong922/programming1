package menu;

public class MainMenu {

    public void startMainMenu() {
        OptionMenu optionMenu = new OptionMenu();
        optionMenu.add(new Option("Lead Menu", "1", () -> {
            LeadMenu leadMenu = new LeadMenu();
            leadMenu.startLeadMenu();
        }));
        optionMenu.add(new Option("Interaction Menu", "2", () -> {
            InteractionMenu interactionMenu = new InteractionMenu();
            interactionMenu.startInteractionMenu();
        }));
        optionMenu.add(new Option("Exit", "3", () -> {
            System.out.println("Program exit.");
        }));
        optionMenu.start();
    }
}
