package com.company;

import menu.ICommand;
import menu.Option;
import menu.OptionMenu;

public class Main {

    public static void main(String[] args) throws Exception {
        OptionMenu optionMenu = new OptionMenu();
        Option leadOption = new Option("Lead", "1", new ICommand() {
            @Override
            public void execute() {

            }
        });
        Option interactionOption = new Option("Interaction", "2", new ICommand() {
            @Override
            public void execute() {
                System.out.println("Hello 1");

            }
        });
        Option exitOption = new Option("Exit", "3", new ICommand() {
            @Override
            public void execute() {
                System.out.println("Quit");

            }
        });
        optionMenu.addOption(leadOption);
        optionMenu.addOption(interactionOption);
        optionMenu.addOption(exitOption);
        optionMenu.start();

    }
}
