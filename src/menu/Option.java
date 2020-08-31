package menu;

public class Option {
    private String label;
    private String toggleKey;

    public Option(String label, String toggleKey) {
        this.label = label;
        this.toggleKey = toggleKey;
    }

    public void execute(ICommand command) {
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
