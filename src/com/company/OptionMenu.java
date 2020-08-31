package com.company;

import java.util.ArrayList;

public class OptionMenu {
    private ArrayList<Option> options = new ArrayList<>();

    public void addOption(Option option) {
        options.add(option);
    }

    public OptionMenu() {

    }

    public void display() {
        for (Option option:options) {
            System.out.println(option.getTitle());
        }
    }

    public void prompt() {

    }
}
