package menu;

import java.lang.reflect.Field;

public class TableFormatter {
    private String[] labels;
    private String[][] rows;
    private static String sep = " | ";
    public TableFormatter(String[] labels, String[][] rows) {
        this.labels = labels;
        this.rows = rows;
    }

    public void display() {
        printTableBorder();
        printTableRow(labels);
        printTableBorder();
        for (String[] row : rows) {
            printTableRow(row);
        }
        printTableBorder();
    }

    private void printTableRow(String[] row) {
        int[] columnsLength = getColumnsLength();
        System.out.print(sep);
        for (int i = 0; i < row.length; i++) {
            String field = row[i];
            System.out.print(field);
            printMultiple(" ", columnsLength[i] - field.length());
            System.out.print(sep);
        }
        System.out.println("");
    }

    public int[] getColumnsLength() {
        int[] length = new int[labels.length];
        for (int i = 0; i < labels.length; i++) {
            length[i] = labels[i].length();
        }

        for (String[] row : rows) {
            for (int i = 0; i < labels.length; i++) {
                length[i] = Math.max(row[i].length(), length[i]);
            }
        }
        return length;
    }
    public void printTableBorder(){
        System.out.print(" ");
        printMultiple("-",getWidth()-2);
        System.out.print(" ");
        System.out.println("");
    }
    public int getWidth() {
        int width = (labels.length + 1)*sep.length();
        for (int columnLength : getColumnsLength()) {
            width += columnLength;
        }
        return width;
    }

    public static void printMultiple(String s, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(s);
        }
    }
}
