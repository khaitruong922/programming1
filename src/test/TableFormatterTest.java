package test;

import menu.TableFormatter;

import java.util.Arrays;

public class TableFormatterTest {
    public static void main(String[] args) {
        String[] labels = new String[]{"Id", "Name", "Phone"};
        TableFormatter tableFormatter = new TableFormatter(labels);
        tableFormatter.addRow(new String[]{"Hello","Khaiiii","090"});
        tableFormatter.addRow(new String[]{"Hello","Khaiiiiiiii","090"});
        tableFormatter.display();
    }
}
