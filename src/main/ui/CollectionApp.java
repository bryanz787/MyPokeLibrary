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


//        boolean stop = false;
//        String command;
//
//
//        load();
//
//        while (!stop) {
//            mainMenu();
//
//            command = input.next().toLowerCase();
//            if (command.equals("q")) {
//                stop = true;
//            } else {
//                redirect(command);
//            }
//        }
//        save();
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
                System.out.println("\nThanks for using My PokeLibrary, see you next time!");
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

    //EFFECTS: returns a string summarizing important statistics about the users collection
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
