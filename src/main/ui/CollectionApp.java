package ui;

import model.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

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
        switch (command) {
            case "p":
                return addPkmn();
            case "t":
                return addTrainer();
            case "e":
                return addEnergy();
        }
    }

    //EFFECTS: Initializes a new energyCard object according to inputs
    public Card addPkmn() {
        String pokeName;
        String pokeType;
        Boolean holofoil;
        int hitPoints;
        int stage;
        List<Move> moves;

        System.out.println("\nEnter the pokemons name:");
        pokeName = input.next();

        System.out.println("\nEnter the pokemons type color as shown on the card:");
        typeMenu();
        pokeType = input.next();

        System.out.println("\nIs the card a holofoil? (y/n)");
        holofoil = holofoilProcess(input.next());

        System.out.println("\nHow many hitpoints does it have?");
        hitPoints = input.nextInt();

        System.out.println("\nWhat stage evolution is the card? If basic type 0");
        stage = input.nextInt();

        moves = addMoves();

        return new PokemonCard(pokeName, pokeType, holofoil, hitPoints, stage, moves);

    }

    private void typeMenu() {
        System.out.println("R - red, fire\n" +
                "BR - orange/brown, fighting, rock, ground\n" +
                "GO - gold, dragon\n" +
                "Y - yellow, electric\n" +
                "GR - green, grass, bug, poison\n" +
                "BL - blue, water, ice\n" +
                "PI - pink, fairy\n" +
                "PU - purple, psychic, poison, ghost\n" +
                "BL - black, dark, poison\n" +
                "S - silver, steel\n" +
                "W - white, normal, dragon, flying");
    }

    private Boolean holofoilProcess(String input) {
        if ("yes".contains(input)) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    public List<Move> addMoves() {
        int numMoves;
        List<Move> moves = new ArrayList<Move>();

        System.out.println("\nHow many moves does the pokemon have?");
        numMoves = input.nextInt();

        for (int i = 0; i < numMoves; i++) {
            String moveName;
            int damage;
            String effects;

            System.out.println("\nInput the move name");
            moveName = input.next();
            System.out.println("\nInput the moves damage");
            damage = input.nextInt();
            System.out.println("\nInput the move effects, if none press enter");
            effects = input.next();
            if (effects.isBlank()) {
                effects = "No Additional Effcts";
            }

            moves.add(new Move(moveName, damage, effects));
        }

        return moves;
    }

    //EFFECTS: Initializes a new trainerCard object according to inputs
    public Card addTrainer() {
    }

    //EFFECTS: Initializes a new pokemonCard object according to inputs
    public Card addEnergy() {
    }









}
