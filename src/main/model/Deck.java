package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//A deck is a collection of cards, a deck is of arbitrary length but a legal deck is exactly 60 cards
public class Deck {
    private List<Card> deck;
    private String deckName;

    //EFFECTS:a constructor of a deck of pokemon cards,
    public Deck(String name) {
        deck = new ArrayList<Card>();
        deckName = name;
    }

    //MODIFIES: this
    //EFFECTS: adds given card to deck
    public void addCard(Card toAdd) {
        deck.add(toAdd);
    }

    //EFFECTS:returns deck size
    public int deckSize() {
        return deck.size();
    }

    //EFFECTS: returns the card at a given index
    public Card getCard(int index) {
        return deck.get(index);
    }

    //EFFECTS: returns the name of the deck
    public String getDeckName() {
        return deckName;
    }

    //EFFECTS: returns whether the deck contains a legal amount of cards
    public boolean legalLength() {
        return deckSize() == 60;
    }

    //EFFECTS: returns whether the deck contains a basic card or not
    public boolean containsBase() {
        boolean answer = false;

        for (Card c : deck) {
            if (c instanceof PokemonCard) {
                if (((PokemonCard) c).getStage() == 0) {
                    answer = true;
                }
            }
        }

        return answer;
    }

    //REQUIRES: uniqueNames and copies must have the same length
    //EFFECTS: returns whether the deck contains too many duplicates of a pokemon card
    public boolean legalDuplicateCount() {
        boolean answer = true;
        List<List> nameCount = countCard();

        for (Object i : nameCount.get(1)) {
            if ((int) i > 4) {
                answer = false;
                break;
            }
        }

        return answer;
    }

    public List<List> countCard() {
        List<String> uniqueName = new ArrayList<String>();
        List<Integer> copies = new ArrayList<Integer>();
        List<List> orderedCounts = new ArrayList<List>();

        for (Card c : deck) {
            if (!c.getName().contains("energy")) {
                Integer nameIndex = null;

                if (!uniqueName.contains(c.getName())) {
                    uniqueName.add(c.getName());
                    copies.add(1);
                } else {
                    for (int j = 0; j < uniqueName.size(); j++) {
                        if (uniqueName.get(j).equals(c.getName())) {
                            nameIndex = j;
                        }
                    }
                    copies.set(nameIndex, copies.get(nameIndex) + 1);
                }
            }
        }

        orderedCounts.add(uniqueName);
        orderedCounts.add(copies);

        return orderedCounts;
    }

    //EFFECTS: returns whether the deck is legal to play or not
    public boolean legalDeck() {
        return legalLength() && containsBase() && legalDuplicateCount();
    }

    public Integer energyCount() {
        Integer count = 0;
        for (Card c : deck) {
            if (c instanceof EnergyCard) {
                count++;
            }
        }
        return count;
    }
}
