package test;

import menu.TableFormatter;

import java.util.Arrays;

public class TableFormatterTest {
    public static void main(String[] args) {
        String[] labels = new String[]{"Id", "Name", "Phone"};
        String[][] rows = new String[][]{
                new String[]{"lead_007", "Khai", "0908321238"},
                new String[]{"lead_008", "Khaiiiiiiiii", "0908323238"}
        };


        TableFormatter tableFormatter = new TableFormatter(labels, rows);
        tableFormatter.display();
    }
}
