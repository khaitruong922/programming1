package com.company;

public class Option {
    private String label;
    private String toggleKey;

    public Option(String label, String toggleKey) {
        this.label = label;
        this.toggleKey = toggleKey;
    }

    public void execute() {
        System.out.println("Do something");
    }

    public String getLabel() {
        return label;
    }

    public String getToggleKey() {
        return toggleKey;
    }

    public String getTitle() {
        return toggleKey + ". " +label;
    }
}
