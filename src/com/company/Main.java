package com.company;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SecureCacheResponse;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        OptionMenu optionMenu = new OptionMenu();
        Option leadOption = new Option("Lead","1");
        Option interactionOption = new Option("Interaction","2");
        Option exitOption = new Option("Exit","3");
        optionMenu.addOption(leadOption);
        optionMenu.addOption(interactionOption);
        optionMenu.addOption(exitOption);
        optionMenu.display();

    }
}
