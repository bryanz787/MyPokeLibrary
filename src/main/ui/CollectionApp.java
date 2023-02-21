package ui;

import model.*;
import java.util.Scanner;

public class CollectionApp {

    private String userName;
    private Collection cardsList;
    private Scanner input;

    //EFFECTS: runs the pokemon card collection app
    public CollectionApp() {
        runCollectionApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runCollectionApp() {
        Boolean stop = false;
        String command = null;

        init();

    }

    //MODIFIES: this
    //EFFECTS: initializes fields, gets users username
    private void init() {
        cardsList = new Collection();
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        System.out.println("Welcome to your PokeLibrary, your convenient pokemon card collection app!\n" +
                "Before we start, please input a username.");
        userName = input.next();
        System.out.println("\nWelcome " + userName + "!");
    }

}
