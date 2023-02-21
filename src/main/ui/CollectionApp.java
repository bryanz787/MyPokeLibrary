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

        while (!stop) {
            mainMenu();

            command = input.next().toLowerCase();
            if (command == "q") {
                stop = true;
            } else {
                redirect(command);
            }

        }

        System.out.println("Thanks for using My PokeLibrary, see you next time!");
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

    //EFFECTS: Prints out the directory for the various options user could take
    public void mainMenu() {
        System.out.println(userName + ", input one of the options below: \n" +
                "A - Add card to collection \n" +
                "V - View the cards in your collection\n" +
                "D - Create a new deck\n" +
                "Q - Quit application\n");
    }

    //MODIFIES: this
    //EFFECTS: processes users command
    public void redirect(String command) {
        if (command.equals("A")) {
            System.out.println("Done A"); //addCards
        } else if (command.equals("V")) {
            System.out.println("Done V"); //view
        } else if (command.equals("D")) {
            System.out.println("Done D"); //deck (hard)
        } else {
            System.out.println("Input not recognized, please type in a recognized input\n");
        }
    }

    //MODIFIES: cardsList
    //EFFECTS: Adds one or multiple of the same card to your collection
    public void addCards() {
        int toAddCount;
        String command;

        System.out.println("What type of pokemon card are you trying to add?\n" + "P - Pokemon cards\n" +
                "T - Trainer cards\n" + "E - Energy cards\n" + "R - Return to main menu\n");
        command = input.next().toLowerCase();

        System.out.println("\nHow many copies of that card do you want to add?");
        toAddCount = input.nextInt();

        if (!command.equals("r") && "pte".contains(command)) {
            Card toAdd = toAddCreator(command);

            for (int i = 0; i < toAddCount; i++) {
                cardsList.addCard(toAdd);
            }

            System.out.println("\n" + String.valueOf(toAddCount) + " cards successfully added to collection!\n");
        } else if (!command.equals("r")) {
            System.out.println("\nInput not recognized, redirecting back to main menu...\n");
        } else {
            System.out.println("\nRedirecting back to main menu...\n");
        }
    }

    //EFFECTS: constructs and returns the proper card type
    public Card toAddCreator(String command) {
        /*if (command.equals()) {

        } else if () {

        } else {

        }*/
        return new EnergyCard("placeholder", false);
    }

    //addPkmn, addTrainer, addEnergy

    //EFFECTS: Initializes a new pokemonCard object according to inputs
    //EFFECTS: Initializes a new trainerCard object according to inputs
    //EFFECTS: Initializes a new energyCard object according to inputs

}
