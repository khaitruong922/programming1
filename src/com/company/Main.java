package com.company;

import menu.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String[] labels = new String[]{"Id", "Name", "Phone"};
        String[][] rows = new String[][]{
                new String[]{"lead_007","Khai","0908321238"},
                new String[]{"lead_008","Khaiiiiii","0908323238"}
        };
        TableFormatter tableFormatter = new TableFormatter(labels, rows);
        tableFormatter.display();
    }
}
