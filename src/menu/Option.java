package menu;

public class Option {
    private final String label;
    private final String toggleKey;
    private final ICommand command;

    public Option(String label, String toggleKey, ICommand command) {
        this.label = label;
        this.toggleKey = toggleKey;
        this.command = command;
    }

    public void execute() {
        command.execute();
    }

    public String getLabel() {
        return label;
    }

    public String getToggleKey() {
        return toggleKey;
    }

    public String getTitle() {
        return toggleKey + ". " + label;
    }
}
