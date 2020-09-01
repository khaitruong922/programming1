package menu;

public class TableFormatter {
    private String[] labels;
    private String[][] rows;

    public TableFormatter(String[] labels, String[][] rows) {
        this.labels = labels;
        this.rows = rows;
    }

    public void display() {
        for (String label : labels) {
            System.out.print(label);
            System.out.print(" ");
        }
        System.out.println("");
        for (String[] row : rows) {
            for (String field : row) {
                System.out.print(field);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
}
