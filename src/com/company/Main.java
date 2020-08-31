package com.company;

import menu.Option;
import menu.OptionMenu;

public class Main {

    public static void main(String[] args) throws Exception {
        OptionMenu optionMenu = new OptionMenu();
        Option leadOption = new Option("Lead","1");
        Option interactionOption = new Option("Interaction","2");
        Option exitOption = new Option("Exit","3");
        optionMenu.addOption(leadOption);
        optionMenu.addOption(interactionOption);
        optionMenu.addOption(exitOption);
        optionMenu.start();

    }
}
