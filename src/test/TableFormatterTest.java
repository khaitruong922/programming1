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
        int[] columnsLength = tableFormatter.getColumnsLength();
        int[] expected = new int[]{8, 12, 10};
        boolean success = true;
        for (int i = 0; i < expected.length; i++) {
            boolean isEqual = columnsLength[i] == expected[i];
            System.out.println(columnsLength[i] + "==" + expected[i] + ": " + isEqual);
            if (!isEqual) success = false;
        }
        System.out.println(success ? "Test success." : "Test failed");

        tableFormatter.display();
    }
}
