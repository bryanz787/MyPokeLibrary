package ui;

import model.*;
import model.Event;
import org.json.JSONArray;
import org.json.JSONObject;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


//Represents the collection app, provides the user with interactions and helps them navigate
public class CollectionApp extends JFrame {
    // Application frame variables
    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;
    public static final String[] types = {"[Select]", "Fire", "Fighting", "Dragon", "Electric", "Gras", "Water",
            "Fairy", "Psychic", "Dark", "Steel", "Normal"};
    public static final String[] foils = {"[Select]", "True", "False"};
    public static final String[] stages = {"[Select]", "0", "1", "2"};

    //where the users data will be saved and read from
    private static final String JSON_STORE = "./data/userFile.json";

    //For displaying card in terminal
    private static final int CARDS_PER_PAGE = 10;

    private String userName;
    private Collection cardsList;
    private List<Deck> deckList;
    private Scanner input;

    private JsonWriter writer;
    private JsonReader reader;

    private JFrame appInterface;
    private JSplitPane splitScreen;
    private DefaultListModel modelList;
    private JList<String> listComponents;


    //EFFECTS: runs the pokemon card collection app
    public CollectionApp() throws FileNotFoundException {
        runCollectionApp();
    }

    //EFFECTS: secondary constructor used for testing, initalizes the values of the fields but doesnt run ui
    public CollectionApp(String userName, Collection cardsList, List<Deck> deckList) {
        this.userName = userName;
        this.cardsList = cardsList;
        this.deckList = deckList;
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runCollectionApp() {
        initClassAndPersistance();
        initJComponents();

        displayMainMenu();


        boolean stop = false;
        String command;


        load();

        while (!stop) {
            mainMenu();

            command = input.next().toLowerCase();
            if (command.equals("q")) {
                stop = true;
            } else {
                redirect(command);
            }
        }
        save();

        System.out.println("\nThanks for using My PokeLibrary, see you next time!");
    }

    //MODIFIES: this
    //EFFECTS: initializes fields, reader, and writer
    private void initClassAndPersistance() {
        cardsList = new Collection();
        deckList = new ArrayList<Deck>();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        writer = new JsonWriter(JSON_STORE);
        reader = new JsonReader(JSON_STORE);
    }

    // Modifies: this
    //EFFECTS: initializes objects to load gui
    private void initJComponents() {
        appInterface = new JFrame();
        splitScreen = new JSplitPane();

        appInterface.setLayout(new BorderLayout());
        appInterface.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        splitScreen.setSize(new Dimension(WIDTH, HEIGHT));
        appInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appInterface.setLocationRelativeTo(null);
        appInterface.setVisible(true);
        appInterface.setBackground(new Color(180, 180, 180));
        appInterface.add(splitScreen, BorderLayout.CENTER);

        JLabel selectPrompt = new JLabel("Please select an action to perform.");
        splitScreen.setRightComponent(selectPrompt);
    }

    //Modifies: this
    //EFFECTS: Displays the buttons on the left panel
    private void displayMainMenu() {
        JPanel layout = new JPanel(new BorderLayout());
        JPanel menuButtons = new JPanel();
        JLabel greeting = makeGreeting();

        menuButtons.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel buttonPanel = new JPanel(new GridLayout(11, 1, 10, 5));
        buttonPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        //adds the buttons for each option
        viewMyCollection(buttonPanel);
        addPokemonToCollection(buttonPanel);
        addTrainerToCollection(buttonPanel);
        addEnergyToCollection(buttonPanel);
        viewMyDecks(buttonPanel);
        addToDecks(buttonPanel);
        removeFromDecks(buttonPanel);
        saveState(buttonPanel);
        loadState(buttonPanel);
        quit(buttonPanel);
        menuButtons.add(buttonPanel);

        layout.add(greeting, BorderLayout.NORTH);
        layout.add(menuButtons, BorderLayout.CENTER);

        splitScreen.setLeftComponent(layout);
        layout.revalidate();
    }

    //EFFECT: creates the text displayed above the menu of buttons
    public JLabel makeGreeting() {
        JLabel greeting = new JLabel();
        greeting.setText("<html><center>Welcome to MyPokeLibrary!<br>"
                + "Select an option below to proceed.</center></html>");
        greeting.setBorder(new EmptyBorder(30, 10, 0, 0));

        return greeting;
    }

    //MODIFIES: this
    //EFFECTS: makes buttons for menu to view cards
    private void viewMyCollection(JPanel buttonPanel) {
        JButton butt = new JButton("View My Cards");
        butt.addActionListener(new ViewCardsListener());
        buttonPanel.add(butt);
    }

    // EFFECTS: class representing listener for the collection viewer button
    class ViewCardsListener implements ActionListener {

        //EFFECTS: waits for action and then displays cards accordingly
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel layout = new JPanel(new GridLayout(2, 1, 0, 10));
            modelList = new DefaultListModel<>();
            listComponents = new JList<>();

            if (cardsList.collectionSize() == 0) {
                JLabel noCardsMsg = new JLabel("<html><center>Oops! It looks like you dont have any cards<br>"
                        + "in your collection, try adding some!</center></html>");
                noCardsMsg.setBorder(new EmptyBorder(0, 30, 0, 0));
                layout.add(noCardsMsg, BorderLayout.CENTER);
            } else {
                modelList = cardsList.addCards(modelList);

                listComponents.setModel(modelList);
                JScrollPane scrollPane = new JScrollPane(listComponents);
                layout.add(scrollPane, BorderLayout.CENTER);

                layout.add(getSummaryPanel(), BorderLayout.SOUTH);

                layout.setBorder(BorderFactory.createTitledBorder("Your Cards:"));
            }
            splitScreen.setRightComponent(layout);
            layout.revalidate();
        }

        //EFFECTS: helper to create stats component
        private JTextArea getSummaryPanel() {
            JTextArea summaryStats = new JTextArea(getCollectionStats());
            summaryStats.setEditable(false);
            summaryStats.setBackground(new Color(180, 180, 180));
            summaryStats.setPreferredSize(new Dimension(400, 50));
            return summaryStats;
        }

        /*//EFFECTS: helper to get items to display
        private void addCardToList(int i) {
            Card toAdd = cardsList.getCard(i);
            modelList.addElement(toAdd.toString());
        }*/
    }

    //MODIFIES: this
    //EFFECTS: makes buttons for menu to add cards
    private void addPokemonToCollection(JPanel buttonPanel) {
        JButton butt = new JButton("Add A Pokemon Card");
        butt.addActionListener(new AddPokeCardsListener());
        buttonPanel.add(butt);
    }

    // MODIFIES: cardsList
    // EFFECTS: gives user option to input fields for pokemonCard,
    //          creates a form with fields to create a new card
    //          when clicked on submit:
    //          creates card of pokemon class and adds to collection
    class AddPokeCardsListener implements ActionListener {
        private JButton submitButton;
        JTextField pokemonName = new JTextField();
        String type;
        String holo;
        String stage;
        JTextField hitpoints = new JTextField();

        //components for pokemon card
        JLabel pokemonNameLabel = new JLabel("");
        JLabel typeLabel;
        JLabel holoLabel;
        JLabel stageLabel;
        JLabel hitpointsLabel = new JLabel("");

        JPanel namePanel;
        JPanel typePanel;
        JPanel holoPanel;
        JPanel stagePanel;
        JPanel hpPanel;

        JComboBox<String> typeBox;
        JComboBox<String> foilBox;
        JComboBox<String> stageBox;

        JPanel submitPanel;
        JPanel layout = new JPanel(new GridBagLayout());
        JPanel inputFieldPane = new JPanel(new GridLayout(6, 1));

        ImageIcon img = new ImageIcon("data/pokeball.png");
        Image imgimg = img.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon imgIcon = new ImageIcon(imgimg);

        //EFFECTS: adds action for the submit button
        public AddPokeCardsListener() {
            submitButton = new JButton("Submit");
            SubmitButtonListener submitCard = new SubmitButtonListener();
            submitButton.addActionListener(submitCard);
        }

        //MODIFIES: cardslist
        //EFFECTS: create gui that allows for user to add pokemon card
        @Override
        public void actionPerformed(ActionEvent e) {
            inputFieldPane.removeAll();
            inputFieldPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            createCardNameAndInput();
            createTypeNameAndInput();
            createFoilNameAndInput();
            createStageNameAndInput();
            createHitpointsAndInput();

            submitPanel = new JPanel(new GridLayout(2, 1));
            submitPanel.add(submitButton);
            inputFieldPane.add(submitPanel);

            layout.add(inputFieldPane);

            splitScreen.setRightComponent(layout);
            layout.revalidate();
        }

        public void createCardNameAndInput() {
            pokemonName.setText("");
            pokemonNameLabel.setText("Card Name");
            pokemonNameLabel.setPreferredSize(new Dimension(200, 15));

            namePanel = new JPanel(new GridLayout(2, 1));
            namePanel.add(pokemonNameLabel);
            namePanel.add(pokemonName);

            inputFieldPane.add(namePanel);
        }

        public void createTypeNameAndInput() {
            typeBox = new JComboBox<>(types);
            typeBox.setSelectedIndex(0);

            typeBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Update the type variable whenever a new option is selected
                    type = (String) typeBox.getSelectedItem();
                }
            });

            typeLabel = new JLabel("Type");
            typePanel = new JPanel(new GridLayout(2, 1));
            typePanel.add(typeLabel);
            typePanel.add(typeBox);

            inputFieldPane.add(typePanel);
        }

        public void createFoilNameAndInput() {
            foilBox = new JComboBox<>(foils);
            foilBox.setSelectedIndex(0);

            foilBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Update the type variable whenever a new option is selected
                    holo = (String) foilBox.getSelectedItem();
                }
            });

            holoLabel = new JLabel("Holofoil");
            holoPanel = new JPanel(new GridLayout(2, 1));
            holoPanel.add(holoLabel);
            holoPanel.add(foilBox);

            inputFieldPane.add(holoPanel);
        }

        public void createStageNameAndInput() {
            stageBox = new JComboBox<>(stages);
            stageBox.setSelectedIndex(0);

            stageBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Update the type variable whenever a new option is selected
                    stage = (String) stageBox.getSelectedItem();
                }
            });

            stageLabel = new JLabel("Evolution Stage");
            stagePanel = new JPanel(new GridLayout(2, 1));
            stagePanel.add(stageLabel);
            stagePanel.add(stageBox);

            inputFieldPane.add(stagePanel);
        }

        public void createHitpointsAndInput() {
            hitpoints.setText("");
            hitpointsLabel.setText("Hitpoints (number only)");
            hitpointsLabel.setPreferredSize(new Dimension(200, 15));

            hpPanel = new JPanel(new GridLayout(2, 1));
            hpPanel.add(hitpointsLabel);
            hpPanel.add(hitpoints);

            inputFieldPane.add(hpPanel);
        }

        // MODIFIES: cardsList
        // EFFECTS: adds new pokemon card object with specified fields
        class SubmitButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                String pokeName = pokemonName.getText();
                Integer hp = Integer.parseInt(hitpoints.getText());
                Card toAdd = new PokemonCard(pokeName, type, Boolean.valueOf(holo),
                        hp, Integer.parseInt(stage));
                cardsList.addCard(toAdd);


                JPanel container = new JPanel(new GridBagLayout());
                JLabel successfullyMsg = new JLabel("Successfully added");
                successfullyMsg.setOpaque(false);
                JLabel successfulIcon = new JLabel();
                successfulIcon.setIcon(imgIcon);
                container.add(successfulIcon);
                container.add(successfullyMsg);
                splitScreen.setRightComponent(container);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: makes buttons for menu to add trainercards
    private void addTrainerToCollection(JPanel buttonPanel) {
        JButton butt = new JButton("Add A Trainer Card");
        butt.addActionListener(new AddTrainerCardsListener());
        buttonPanel.add(butt);
    }

    // MODIFIES: cardsList
    // EFFECTS: gives user option to input fields for a trainer Card,
    //          creates a form with fields to create a new card
    //          when clicked on submit:
    //          creates card of proper trainer class and adds to collection
    class AddTrainerCardsListener implements ActionListener {
        private JButton submitButton;
        JTextField trainerName = new JTextField();
        String holo;
        JTextField effects = new JTextField();

        //components for trainer card
        JLabel trainerNameLabel = new JLabel("");
        JLabel holoLabel;
        JLabel effectsLabel = new JLabel("");

        JPanel namePanel;
        JPanel holoPanel;
        JPanel effectsPanel;

        JComboBox<String> foilBox;

        JPanel submitPanel;
        JPanel layout = new JPanel(new GridBagLayout());
        JPanel inputFieldPane = new JPanel(new GridLayout(4, 1));

        ImageIcon img = new ImageIcon("data/pokeball.png");
        Image imgimg = img.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon imgIcon = new ImageIcon(imgimg);

        public AddTrainerCardsListener() {
            submitButton = new JButton("Submit");
            SubmitButtonListener submitReview = new SubmitButtonListener();
            submitButton.addActionListener(submitReview);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            inputFieldPane.removeAll();
            inputFieldPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            createCardNameAndInput();
            createFoilNameAndInput();
            createEffectsAndInput();

            submitPanel = new JPanel(new GridLayout(2, 1));
            submitPanel.add(submitButton);
            inputFieldPane.add(submitPanel);

            layout.add(inputFieldPane);

            splitScreen.setRightComponent(layout);
            layout.revalidate();
        }

        public void createCardNameAndInput() {
            trainerName.setText("");
            trainerNameLabel.setText("Card Name");
            trainerNameLabel.setPreferredSize(new Dimension(200, 15));

            namePanel = new JPanel(new GridLayout(2, 1));
            namePanel.add(trainerNameLabel);
            namePanel.add(trainerName);

            inputFieldPane.add(namePanel);
        }

        public void createFoilNameAndInput() {
            foilBox = new JComboBox<>(foils);
            foilBox.setSelectedIndex(0);

            foilBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Update the type variable whenever a new option is selected
                    holo = (String) foilBox.getSelectedItem();
                }
            });

            holoLabel = new JLabel("Holofoil");
            holoPanel = new JPanel(new GridLayout(2, 1));
            holoPanel.add(holoLabel);
            holoPanel.add(foilBox);

            inputFieldPane.add(holoPanel);
        }

        public void createEffectsAndInput() {
            effects.setText("");
            effectsLabel.setText("Card Effects");
            effectsLabel.setPreferredSize(new Dimension(200, 15));

            effectsPanel = new JPanel(new GridLayout(2, 1));
            effectsPanel.add(effectsLabel);
            effectsPanel.add(effects);

            inputFieldPane.add(effectsPanel);
        }


        // MODIFIES: cardslist
        // EFFECTS: adds new trainerCard object with specified fields
        class SubmitButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                String trainName = trainerName.getText();
                String effect = effects.getText();
                Card toAdd = new TrainerCard(trainName, Boolean.valueOf(holo), effect);
                cardsList.addCard(toAdd);

                JPanel container = new JPanel(new GridBagLayout());
                JLabel successfullyMsg = new JLabel("Successfully added");
                successfullyMsg.setOpaque(false);
                JLabel successfulIcon = new JLabel();
                successfulIcon.setIcon(imgIcon);
                container.add(successfulIcon);
                container.add(successfullyMsg);
                splitScreen.setRightComponent(container);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: makes buttons for menu to add cards
    private void addEnergyToCollection(JPanel buttonPanel) {
        JButton butt = new JButton("Add An Energy Card");
        butt.addActionListener(new AddEnergyCardsListener());
        buttonPanel.add(butt);
    }

    // MODIFIES: cardsList
    // EFFECTS: gives user option to input fields for energyCard,
    //          creates a form with fields to create a new card
    //          when clicked on submit:
    //          creates card of energy class and adds to collection
    class AddEnergyCardsListener implements ActionListener {
        private JButton submitButton;
        String type;
        String holo;

        //components for energy card
        JLabel typeLabel;
        JLabel holoLabel;

        JPanel typePanel;
        JPanel holoPanel;

        JComboBox<String> typeBox;
        JComboBox<String> foilBox;

        JPanel submitPanel;
        JPanel layout = new JPanel(new GridBagLayout());
        JPanel inputFieldPane = new JPanel(new GridLayout(3, 1));

        ImageIcon img = new ImageIcon("data/pokeball.png");
        Image imgimg = img.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon imgIcon = new ImageIcon(imgimg);

        public AddEnergyCardsListener() {
            submitButton = new JButton("Submit");
            SubmitButtonListener submitReview = new SubmitButtonListener();
            submitButton.addActionListener(submitReview);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            inputFieldPane.removeAll();
            inputFieldPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            createTypeNameAndInput();
            createFoilNameAndInput();

            submitPanel = new JPanel(new GridLayout(2, 1));
            submitPanel.add(submitButton);
            inputFieldPane.add(submitPanel);

            layout.add(inputFieldPane);

            splitScreen.setRightComponent(layout);
            layout.revalidate();
        }

        public void createTypeNameAndInput() {
            typeBox = new JComboBox<>(types);
            typeBox.setSelectedIndex(0);

            typeBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Update the type variable whenever a new option is selected
                    type = (String) typeBox.getSelectedItem();
                }
            });

            typeLabel = new JLabel("Type");
            typePanel = new JPanel(new GridLayout(2, 1));
            typePanel.add(typeLabel);
            typePanel.add(typeBox);

            inputFieldPane.add(typePanel);
        }

        public void createFoilNameAndInput() {
            foilBox = new JComboBox<>(foils);
            foilBox.setSelectedIndex(0);

            foilBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Update the type variable whenever a new option is selected
                    holo = (String) foilBox.getSelectedItem();
                }
            });

            holoLabel = new JLabel("Holofoil");
            holoPanel = new JPanel(new GridLayout(2, 1));
            holoPanel.add(holoLabel);
            holoPanel.add(foilBox);

            inputFieldPane.add(holoPanel);
        }

        // MODIFIES: cardslist
        // EFFECTS: adds new energyCard object with specified fields
        class SubmitButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                Card toAdd = new EnergyCard(type, Boolean.valueOf(holo));
                cardsList.addCard(toAdd);

                JPanel container = new JPanel(new GridBagLayout());
                JLabel successfullyMsg = new JLabel("Successfully added");
                successfullyMsg.setOpaque(false);
                JLabel successfulIcon = new JLabel();
                successfulIcon.setIcon(imgIcon);
                container.add(successfulIcon);
                container.add(successfullyMsg);
                splitScreen.setRightComponent(container);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: makes buttons for menu to view decks
    private void viewMyDecks(JPanel buttonPanel) {
        JButton butt = new JButton("View My Decks");
        butt.addActionListener(new ViewDecksListener());
        buttonPanel.add(butt);
    }


    //Class that extends actionListener and gives user menu to select decks and from that display cards in deck
    class ViewDecksListener implements ActionListener {
        JComboBox deckSelector;
        String[] deckNamesList;
        String deckSelection;
        String[] nameSplit;
        String deckName;
        Deck toView;
        JPanel layout;

        //EFFECTS: waits for action and then displays cards accordingly
        @Override
        public void actionPerformed(ActionEvent e) {
            layout = new JPanel();
            modelList = new DefaultListModel<>();
            listComponents = new JList<>();

            if (deckList.size() == 0) {
                JLabel noDecksMsg = new JLabel("<html><center>Oops! It looks like you dont have any decks<br>"
                        + "in your collection, try adding some!</center></html>");
                noDecksMsg.setBorder(new EmptyBorder(0, 30, 0, 0));
                layout.add(noDecksMsg, BorderLayout.SOUTH);
            } else {
                addDeckNames();
                deckSelector = new JComboBox(deckNamesList);

                deckSelector.addActionListener(deckSelectorListener());

                layout.add(deckSelector);
                listComponents.setModel(modelList);
                JScrollPane scrollPane = new JScrollPane(listComponents);
                scrollPane.setPreferredSize(new Dimension(365, 400));
                layout.add(scrollPane, BorderLayout.SOUTH);

                layout.setBorder(BorderFactory.createTitledBorder("Deck Cards:"));
            }
            splitScreen.setRightComponent(layout);
            layout.revalidate();
        }

        //EFFECTS: creates new action listener for the combobox
        private ActionListener deckSelectorListener() {
            return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addDeckNames();

                    deckSelectorInit();

                    for (Deck d : deckList) {
                        if (d.getDeckName().equals(deckName)) {
                            toView = d;
                        }
                    }

                    for (int i = 0; i < toView.deckSize(); i++) {
                        addCardToList(toView.getCard(i));
                    }

                    layout.add(deckSelector);
                    listComponents.setModel(modelList);
                    JScrollPane scrollPane = new JScrollPane(listComponents);
                    scrollPane.setPreferredSize(new Dimension(365, 400));
                    layout.add(scrollPane, BorderLayout.SOUTH);

                    layout.setBorder(BorderFactory.createTitledBorder("Deck Cards:"));

                    splitScreen.setRightComponent(layout);
                    layout.revalidate();
                }
            };
        }

        //EFFECTS: initializes components used in deckSelector
        private void deckSelectorInit() {
            deckSelection = (String) deckSelector.getSelectedItem();
            nameSplit = deckSelection.split("]");
            deckName = nameSplit[1];
            toView = deckList.get(0);
            layout = new JPanel();
            modelList = new DefaultListModel<>();
        }

        //EFFECTS: helper to get items to display
        private void addCardToList(Card c) {
            modelList.addElement(c.toString());
        }

        //EFFECTS: adds names of all decks to string array
        public void addDeckNames() {
            List<String> deckNames = new ArrayList<>();

            deckNames.add("[Select]");
            for (Deck d : deckList) {
                deckNames.add("[" + legalString(d.legalDeck()) + "]" + d.getDeckName());
            }
            deckNamesList = deckNames.toArray(new String[deckNames.size()]);
        }

        private String legalString(Boolean legal) {
            return legal ? "TCG Legal" : "Not TCG Legal";
        }
    }







    //MODIFIES: this
    //EFFECTS: makes buttons for menu to add decks
    private void addToDecks(JPanel buttonPanel) {
        JButton butt = new JButton("Add A Deck");

        buttonPanel.add(butt);
    }









    //MODIFIES: this
    //EFFECTS: makes buttons for menu to add decks
    private void removeFromDecks(JPanel buttonPanel) {
        JButton butt = new JButton("Remove A Deck");
        butt.addActionListener(new DeckRemoverListener());
        buttonPanel.add(butt);
    }

    //Class that adds functionality to the remove decks function
    class DeckRemoverListener implements ActionListener {
        private JButton submitButton;
        String[] deckNamesList;
        Deck toRemove;

        JComboBox<String> removeSelector;

        JPanel layout = new JPanel(new GridBagLayout());
        JPanel inputFieldPane = new JPanel(new GridLayout(2, 1));

        ImageIcon img = new ImageIcon("data/pokeball.png");
        Image imgimg = img.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon imgIcon = new ImageIcon(imgimg);

        public DeckRemoverListener() {
            submitButton = new JButton("Remove");
            submitButton.addActionListener(new RemoveButtonListener());
        }

        //MODIFIES: deckslist
        //EFFECTS: makes a combobox that displays all the decks
        @Override
        public void actionPerformed(ActionEvent e) {
            inputFieldPane.removeAll();

            addDeckNames();
            removeSelector = new JComboBox(deckNamesList);
            removeSelector.addActionListener(deckSelectorListener());

            inputFieldPane.add(removeSelector);
            inputFieldPane.add(submitButton);
            layout.add(inputFieldPane);

            splitScreen.setRightComponent(layout);
            layout.revalidate();
        }

        //EFFECTS: adds names of all decks to string array
        public void addDeckNames() {
            List<String> deckNames = new ArrayList<>();
            deckNames.add("[Select]");
            for (Deck d : deckList) {
                deckNames.add("[" + legalString(d.legalDeck()) + "]" + d.getDeckName());
            }
            deckNamesList = deckNames.toArray(new String[deckNames.size()]);
        }

        private String legalString(Boolean legal) {
            return legal ? "TCG Legal" : "Not TCG Legal";
        }

        //EFFECTS: creates new action listener for the combobox
        private ActionListener deckSelectorListener() {
            return new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String deckSelection = (String) removeSelector.getSelectedItem();
                    String[] nameSplit = deckSelection.split("]");
                    String deckName = nameSplit[1];

                    for (int i = 0; i < deckList.size(); i++) {
                        if (deckList.get(i).getDeckName().equals(deckName)) {
                            toRemove = deckList.get(i);
                        }
                    }
                }
            };
        }

        // MODIFIES: cardslist
        // EFFECTS: adds button that when pressed removes selected deck
        class RemoveButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                deckList.remove(toRemove);
                JPanel container = new JPanel(new GridBagLayout());
                JLabel successfullyMsg = new JLabel("Successfully Removed");
                successfullyMsg.setOpaque(false);
                JLabel successfulIcon = new JLabel();
                successfulIcon.setIcon(imgIcon);
                container.add(successfulIcon);
                container.add(successfullyMsg);
                splitScreen.setRightComponent(container);
            }
        }
    }


    //MODIFIES: this
    //EFFECTS: makes buttons for menu to save state
    private void saveState(JPanel buttonPanel) {
        JButton butt = new JButton("Save Progress");
        butt.addActionListener(new AddSaveStateListener());
        buttonPanel.add(butt);
    }

    // MODIFIES: allReviews.json, reviews.json
    // EFFECTS: writes changes to file, throws exception if file not found
    class AddSaveStateListener implements ActionListener {
        ImageIcon img = new ImageIcon("data/pokeball.png");
        Image imgimg = img.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon imgIcon = new ImageIcon(imgimg);

        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel saveReviewMessage = new JLabel("");
            JPanel container = new JPanel(new GridBagLayout());
            try {
                writer.open();
                writer.write(toJson());
                writer.close();
                saveReviewMessage.setText("Collection Saved");
                JLabel successfulIcon = new JLabel();
                successfulIcon.setIcon(imgIcon);
                container.add(successfulIcon);
                container.add(saveReviewMessage);
            } catch (FileNotFoundException exception) {
                saveReviewMessage = new JLabel("Unable to write to file: " + JSON_STORE);
                container.add(saveReviewMessage);
            } finally {
                splitScreen.setRightComponent(container);
                container.revalidate();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: makes buttons for menu to load state
    private void loadState(JPanel buttonPanel) {
        JButton butt = new JButton("Load Progress");
        butt.addActionListener(new AddLoadStateListener());
        buttonPanel.add(butt);
    }

    // EFFECT: reads state json files and adds appropriate data to classes
    //         throws error if file not found
    class AddLoadStateListener implements ActionListener {
        ImageIcon img = new ImageIcon("data/pokeball.png");
        Image imgimg = img.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon imgIcon = new ImageIcon(imgimg);

        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel loadReviewMessage = new JLabel("");
            JPanel container = new JPanel(new GridBagLayout());

            try {
                userName = reader.readUsername();
                cardsList = reader.readCollection();
                deckList = reader.readDeckList();

                loadReviewMessage.setText("Collection Loaded");
                JLabel successfulIcon = new JLabel();
                successfulIcon.setIcon(imgIcon);
                container.add(successfulIcon);
                container.add(loadReviewMessage);
            } catch (IOException error) {
                loadReviewMessage = new JLabel("Unable to load reviews: " + JSON_STORE);
                container.add(loadReviewMessage);
            } finally {
                splitScreen.setRightComponent(container);
                container.revalidate();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: makes buttons for menu to quit
    private void quit(JPanel buttonPanel) {
        JButton butt = new JButton("Quit");

        butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printEvents();
                System.exit(0);
            }
        });

        buttonPanel.add(butt);
    }

    //EFECTS: prints out contents of event log
    private void printEvents() {
        for (Event next : EventLog.getInstance()) {
            System.out.println("\n" + next.toString());
        }
    }

    //MODIFIES: userName, cardsList, decksList
    //EFFECTS: gives the option of loading in a previous state to the user
    public void load() {
        System.out.println("\nWelcome to your PokeLibrary, your convenient pokemon card collection app!\n"
                + "To get started enter L if you would like to load in the previous save,"
                + " enter anything else to start a new collection");
        String action = input.next().toLowerCase();

        if (action.equals("l")) {
            loadCollectionApp();
        } else {
            greet();
        }
    }

    //MODIFIES: ./data/singlePokeTestFile.json
    //EFFECTS: gives the option of saving the current state to the user
    public void save() {
        System.out.println("\nBefore you go, would you like to save your collection? Keep in mind this overwrites"
                + " the previous save file.");
        System.out.println("Enter S to save the current state, anything else to quit the program");
        String action = input.next().toLowerCase();

        if (action.equals("s")) {
            saveCollectionApp();
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveCollectionApp() {
        try {
            writer.open();
            writer.write(toJson());
            writer.close();
            System.out.println("Saved " + userName + "'s collection data to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadCollectionApp() {
        try {
            userName = reader.readUsername();
            cardsList = reader.readCollection();
            deckList = reader.readDeckList();
            System.out.println("Loaded " + userName + "'s collection from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }




    //--------Code past this point used to implement console based version of application------


    //EFFECTS: Greets user, gets users username
    private void greet() {
        System.out.println("\nBefore we start, please input a username.");
        userName = input.next();
        System.out.println("\nWelcome " + userName + "!");
    }

    //EFFECTS: Prints out the directory for the various options user could take
    public void mainMenu() {
        System.out.println(userName + ", input one of the options below: \n"
                + "A - Add card to collection \n"
                + "V - View the cards in your collection\n"
                + "D - Access deck creator\n"
                + "Q - Quit application\n");
    }

    //MODIFIES: cardslist, deckList
    //EFFECTS: processes users command
    public void redirect(String command) {
        if (command.equals("a")) {
            addCards();
        } else if (command.equals("v")) {
            if (cardsList.collectionSize() == 0) {
                System.out.println("\nIt appears you have no cards in your collection, try adding some first\n");
            } else {
                viewSummary();
            }
        } else if (command.equals("d")) {
            if (cardsList.collectionSize() == 0) {
                System.out.println("\nIt appears you have no cards in your collection, try adding some first\n");
            } else {
                accessDecks();
            }
        } else {
            System.out.println("Input not recognized, try again.");
        }
    }

    //MODIFIES: cardsList
    //EFFECTS: Adds one or multiple of the same card to your collection
    public void addCards() {
        int toAddCount;
        String command;

        System.out.println("\nWhat type of pokemon card are you trying to add?\n" + "P - Pokemon cards\n"
                + "T - Trainer cards\n" + "E - Energy cards\n" + "R - Return to main menu\n");
        command = input.next().toLowerCase();

        if (!command.equals("r") && "pte".contains(command)) {
            Card toAdd = toAddCreator(command);

            System.out.println("\nHow many copies of that card do you want to add?");
            toAddCount = input.nextInt();

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
        Card toAdd = null;
        if (command.equals("p")) {
            toAdd = addPkmn();
        } else if (command.equals("t")) {
            toAdd = addTrainer();
        } else if (command.equals("e")) {
            toAdd = addEnergy();
        }

        return toAdd;
    }

    //EFFECTS: Initializes a new energyCard object according to inputs
    public Card addPkmn() {
        String pokeName;
        String pokeType;
        boolean holofoil;
        int hitPoints;
        int stage;

        System.out.println("\nEnter the pokemons name:");
        pokeName = input.next();

        System.out.println("\nEnter the pokemons type color as shown on the card:");
        typeMenu();
        pokeType = validType(input.next());

        System.out.println("\nIs the card a holofoil? (y/n)");
        holofoil = holofoilStrToBool(input.next());

        System.out.println("\nHow many hitpoints does it have?");
        hitPoints = input.nextInt();

        System.out.println("\nWhat stage evolution is the card? If basic type 0");
        stage = input.nextInt();

        return new PokemonCard(pokeName, pokeType, holofoil, hitPoints, stage);
    }

    //EFFECTS: prints out the list of pokemon/energy types available to the player for selection.
    private void typeMenu() {
        System.out.println("R - red, fire\n"
                + "BR - orange/brown, fighting, rock, ground\n"
                + "GO - gold, dragon\n"
                + "Y - yellow, electric\n"
                + "GR - green, grass, bug, poison\n"
                + "BL - blue, water, ice\n"
                + "PI - pink, fairy\n"
                + "PU - purple, psychic, poison, ghost\n"
                + "BL - black, dark, poison\n"
                + "S - silver, steel\n"
                + "W - white, normal, dragon, flying");
    }

    //EFFECTS: converts the holofoil confirmation from a string to a boolean
    private Boolean holofoilStrToBool(String input) {
        if ("yes".contains(input)) {
            return true;
        } else {
            return false;
        }
    }

    //EFFECTS: converts the holofoil confirmation from a boolean to a string
    private String holofoilBooltoStr(Boolean input) {
        if (input) {
            return "yes";
        } else {
            return "no";
        }
    }

    //EFFECTS: Initializes a new trainerCard object according to inputs
    public Card addTrainer() {
        String cardName;
        boolean holofoil;
        String effects;

        System.out.println("\nEnter the trainer cards name:");
        cardName = input.next();

        System.out.println("\nIs the card a holofoil? (y/n)");
        holofoil = holofoilStrToBool(input.next());

        System.out.println("\nInput the card effects");
        effects = input.next();

        return new TrainerCard(cardName, holofoil, effects);
    }

    //EFFECTS: Initializes a new pokemonCard object according to inputs
    public Card addEnergy() {
        String type;
        boolean holofoil;

        System.out.println("\nEnter the energy cards type color as shown on the card:");
        typeMenu();
        type = validType(input.next().toLowerCase());

        System.out.println("\nIs the energy card a holofoil? (y/n)");
        holofoil = holofoilStrToBool(input.next());

        return new EnergyCard(type, holofoil);
    }

    public String validType(String type) {
        List<String> acceptedType = new ArrayList<String>(
                Arrays.asList("r", "br", "go", "y", "gr", "bl", "pi", "pu", "bl", "s", "w"));
        if (acceptedType.contains(type)) {
            return type;
        } else {
            while (!acceptedType.contains(type)) {
                System.out.println("\nType not recognized, try again");
                type = input.next();
            }
            return type;
        }
    }

    //EFFECTS: redirects the user according to view preferences
    public void viewSummary() {
        String command;

        System.out.println("\nEnter V to view cards page by page, enter S to view collection summary statistics");
        command = input.next().toLowerCase();

        if (command.equals("v")) {
            viewCards();
        } else if (command.equals("s")) {
            showSummary();
        } else {
            System.out.println("\nInput not recognized, redirecting back to main menu");
        }
    }

    //EFFECTS: prints a summary of key stats for a given collection
    public void showSummary() {
        String stats = getCollectionStats();

        System.out.println(stats);
    }

    private String getCollectionStats() {
        int numPokemon = cardsList.countByCriteria("p");
        int numTrainer = cardsList.countByCriteria("t");
        int numEnergy = cardsList.countByCriteria("e");
        int numHolos = cardsList.countByCriteria("h");
        int numBase = cardsList.countByCriteria("b");
        int numFirst = cardsList.countByCriteria("f");
        int numSecond = cardsList.countByCriteria("s");

        return "\nTotal Card Count: " + cardsList.collectionSize()
                + "\nPokemon Card Count: " + numPokemon
                + "\nTrainer Card Count: " + numTrainer
                + "\nEnergy Card Count: " + numEnergy
                + "\nHolo Card Count: " + numHolos
                + "\nBase Stage Card Count: " + numBase
                + "\nFirst Stage Card Count: " + numFirst
                + "\nSecond Stage Card Count: " + numSecond + "\n";
    }

    //EFFECTS: Allows the user to view their collection through input
    public void viewCards() {
        boolean stop = false;

        int pagesAvailable = calculatePages();

        System.out.println("\nThere is a total of " + pagesAvailable + " pages, input which page you'd like to view");
        int pageNum = input.nextInt();

        printCards(pageNum, pagesAvailable, cardsList);

        while (!stop) {
            System.out.println("\nEnter N to view next page, P to view previous page, Q to go back to the main menu");
            String command = input.next().toLowerCase();

            if (command.equals("n") && pageNum < pagesAvailable) {
                pageNum++;
                printCards(pageNum, pagesAvailable, cardsList);
            } else if (command.equals("p") && pageNum > 1) {
                pageNum--;
                printCards(pageNum, pagesAvailable, cardsList);
            } else if (command.equals("q")) {
                stop = true;
            } else if (command.equals("n") || command.equals("p")) {
                System.out.println("\nYou tried to go to a page that doesn't exist, try again.");
            } else {
                System.out.println("\nInput not recognized, redirecting to main menu");
                stop = true;
            }
        }
    }

    //EFFECTS: Determines the number of pages a given collection has
    public int calculatePages() {
        if (cardsList.collectionSize() % CARDS_PER_PAGE == 0) {
            return cardsList.collectionSize() / CARDS_PER_PAGE;
        } else {
            return cardsList.collectionSize() / CARDS_PER_PAGE + 1;
        }
    }

    //EFFECTS: prints out a page of cards in a collection, one at a time
    public void printCards(int pageNum, int pagesAvailable, Collection collection) {
        if (pagesAvailable > pageNum) {
            int start = (pageNum - 1) * 10;

            System.out.println("\nPage " + pageNum);
            for (int i = 0; i < 10; i++) {
                System.out.println((start + i + 1) + ". " + collection.getCard(start + i).getName());
            }

            viewCardInfo();
        } else if (pagesAvailable == pageNum) {
            int start = (pageNum - 1) * 10;

            System.out.println("\nPage " + pageNum);
            for (int i = 0; start + i < collection.collectionSize(); i++) {
                System.out.println((start + i + 1) + ". " + collection.getCard(start + i).getName());
            }

            viewCardInfo();
        } else {
            System.out.println("\nError, entered a number of pages greater than what is available");
        }
    }

    //EFFECTS: prints the complete card info of a given card
    private void viewCardInfo() {
        System.out.println("\nEnter the number of the card if you would like to view a card in more detail"
                + "\nEnter anything else to continue back to page navigation");
        String command = input.next();

        if (!hasLetters(command) && !command.isEmpty()) {
            Integer index = Integer.valueOf(command) - 1;
            Card toView = cardsList.getCard(index);

            if (toView instanceof PokemonCard) {
                System.out.println(toView.getName() + "\nType:" + ((PokemonCard) toView).getPokeType() + "\nHitpoints: "
                        + ((PokemonCard) toView).getHitPoints() + "\nHolofoil: "
                        + holofoilBooltoStr(toView.getHolofoil()));

            } else if (toView instanceof TrainerCard) {
                System.out.println(toView.getName() + "\nHolofoil: " + holofoilBooltoStr(toView.getHolofoil())
                        + "\nEffects: " + ((TrainerCard) toView).getEffects());

            } else if (toView instanceof EnergyCard) {
                System.out.println(toView.getName() + "\nHolofoil: " + holofoilBooltoStr(toView.getHolofoil()));
            }
        }
    }

    //EFFECTS: directs user to deck features based off input
    public void accessDecks() {
        String command;
        String deckName;

        System.out.println("\nEnter A to add a new deck, enter R to remove a deck, enter V to view existing decks,"
                + " enter L to check if your decks are legal to play");
        command = input.next().toLowerCase();

        if (command.equals("a")) {
            if (deckList.size() < 10) {
                System.out.println("\nEnter the name of your new deck");
                deckName = input.next();
                addDeck(deckName);
            } else {
                System.out.println("\nLooks like you have too many decks, try removing one before adding another, "
                        + "redirecting back to main menu");
            }
        } else if (command.equals("r")) {
            removeDeck();
        } else if (command.equals("v")) {
            viewDecks();
        } else if (command.equals("l")) {
            legalCheck();
        } else {
            System.out.println("\nInput not recognized, redirecting back to main menu");
        }
    }

    //MODIFIES: deckList
    //EFFECTS: adds new deck to deck list
    private void addDeck(String deckName) {
        Deck newDeck = new Deck(deckName);
        Boolean stop = false;
        String command;

        while (!stop) {
            System.out.println("\nEnter A to add cards to the deck, enter S to finish and save the new deck");
            command = input.next().toLowerCase();

            while (!(new ArrayList<>(Arrays.asList("a", "s"))).contains(command)) {
                System.out.println("\nInvalid input try again"
                        + "\nEnter A to add cards to the deck, enter S to finish and save the new deck");
                command = input.next().toLowerCase();
            }

            if (command.equals("a")) {
                addCardToDeck(newDeck);
            } else {
                System.out.println("\nDeck saved and added to list of decks");
                stop = true;
            }
        }

        deckList.add(newDeck);
    }

    //MODIFIES: this
    //EFFECTS: adds cards from collection to the deck
    private void addCardToDeck(Deck deck) {
        int pagesAvailable = calculatePages();
        int pageNum = 1;

        printCards(pageNum, pagesAvailable, cardsList);

        while (true) {
            String command = setCommand();

            if (hasLetters(command)) {
                if ((command.equals("n") && pageNum < pagesAvailable) || (command.equals("p") && pageNum > 1)) {
                    pageNum = updatePageNum(command, pageNum);
                    printCards(pageNum, pagesAvailable, cardsList);
                } else if (command.equals("n") || command.equals("p")) {
                    System.out.println("\nYou tried to go to a page that doesn't exist, try again.");
                } else {
                    String toPrint = command.equals("q") ? "Going to deck menu" : "Input invalid, going to deck menu";
                    System.out.println(toPrint);
                    break;
                }
            } else if (Integer.parseInt(command) <= cardsList.collectionSize()) {
                cardAdder(Integer.valueOf(command), deck, pageNum, pagesAvailable);
            } else {
                System.out.println("\nInput number too large, redirecting to deck menu");
                break;
            }
        }
    }

    //EFFECTS: returns string to set command to
    private String setCommand() {
        System.out.println("\nEnter the number of the card you want to add \nEnter N to view next page "
                + "\nEnter P to view previous page \nEnter Q to go back to the deck menu");
        return input.next().toLowerCase();
    }

    //EFFECTS: updates pageNum according to command
    private int updatePageNum(String command, int pageNum) {
        return (command.equals("p")) ? pageNum-- : pageNum++;
    }


    //MODIFIES: deck
    //EFFECTS: adds a card from cardslist into the given deck
    private void cardAdder(Integer command, Deck deck, Integer pageNum, Integer pagesAvailable) {
        Integer copyCount;
        Card toAdd = cardsList.getCard((command - 1));
        System.out.println("\nHow many copies of the card would you like to add to the deck?"
                + "\n(Keep in mind the max legal playable limit is 4 copies if the card is a pokemon)");

        copyCount = input.nextInt();
        while (copyCount < 0) {
            System.out.println("\n" + copyCount + " copies is not a supported amount of inputs, try again");
            copyCount = input.nextInt();
        }

        for (int i = 0; i < copyCount; i++) {
            deck.addCard(toAdd);
        }

        System.out.println("\nSuccess, " + copyCount + " cards added to " + deck.getDeckName());

        printCards(pageNum, pagesAvailable, cardsList);
    }

    //MODIFIES: deckList
    //EFFECTS: removes new deck to deck list
    private void removeDeck() {
        String command;
        Integer toRemove;

        System.out.println("\nEnter the number of the deck you would like to remove, Q if you want to quit");
        for (Deck d : deckList) {
            System.out.println((deckList.indexOf(d) + 1) + " - " + d.getDeckName());
        }

        command = input.next().toLowerCase();
        if (command.contains("q")) {
            System.out.println("\nRedirecting back to main menu");
        } else if (hasLetters(command)) {
            System.out.println("\nInvalid input, redirecting back to main menu");
        } else {
            toRemove = Integer.valueOf(command);
            deckList.remove((toRemove - 1));
            System.out.println("\nDeck removed, redirecting back to main menu");
        }

    }

    //EFFECTS: determines if a given string has alphabet characters in it
    public boolean hasLetters(String s) {
        Boolean answer = false;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i))) {
                answer = true;
            }
        }
        return answer;
    }

    //EFFECTS: displays decks in deck list
    private void viewDecks() {
        int command;

        if (deckList.size() == 0) {
            System.out.println("\nYou have no decks to view, try adding some first");
        } else {
            System.out.println("\nEnter the number of the deck you would like to view");
            for (Deck d : deckList) {
                System.out.println((deckList.indexOf(d) + 1) + " - " + d.getDeckName());
            }

            command = input.nextInt();
            while (!(command > 0 && command <= 10)) {
                System.out.println("\nNumber entered not within 1 to 10, try again");
                command = input.nextInt();
            }

            printDeckCards(command);
        }
    }

    //EFFECTS: prints the counts of each card in a deck
    private void printDeckCards(int command) {
        Deck toView;
        List<String> uniqueName;
        List<Integer> copies;

        toView = deckList.get((command - 1));
        uniqueName = toView.countCard().get(0);
        copies = toView.countCard().get(1);

        System.out.println("");
        for (int i = 0; i < uniqueName.size(); i++) {
            System.out.println(copies.get(i) + "x  " + uniqueName.get(i));
        }
        System.out.println((toView.energyCount()) + "x  assorted energy cards\n");
    }

    //EFFECTS: prints out which decks are legal
    private void legalCheck() {
        for (Deck d : deckList) {
            if (d.legalDeck()) {
                System.out.println("\nDeck " + d.getDeckName() + " is a legal TCG playable deck");
            } else {
                System.out.println("\nDeck " + d.getDeckName() + " is not a legal TCG playable deck");
            }
        }
        System.out.println("");
    }

    //EFFECTS: converts the state of the collectionApp to a jsonObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Deck d : deckList) {
            jsonArray.put(d.toJson());
        }

        json.put("userName", "bryan");
        json.put("cardsList", cardsList.toJson());
        json.put("decksList", jsonArray);

        return json;

    }
}
