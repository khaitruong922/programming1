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
//        Menu.start();
        System.out.println(Lead.example.toCSV());
        System.out.println(Interaction.example.toCSV());
    }
}
