package test;

import database.Database;
import database.Interaction;
import database.Lead;

import java.util.Arrays;

public class DatabaseTest {
    public static void main(String[] args) {
        Database leadDatabase = new Database(Lead.fileName);
        Database interactionDatabase = new Database(Interaction.fileName);
        System.out.println(Arrays.toString(leadDatabase.getAllIds()));
        System.out.println(Arrays.toString(interactionDatabase.getColumn(2)));
    }
}
