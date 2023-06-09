package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

//A deck is a collection of cards, a deck is of arbitrary length, a legal deck is exactly 60 cards amongst other things
public class Deck implements Writable {
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
            if (!c.getName().contains("Energy")) {
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

    //EFFECTS: Returns the number of energy cards in the deck
    public Integer energyCount() {
        Integer count = 0;
        for (Card c : deck) {
            if (c instanceof EnergyCard) {
                count++;
            }
        }
        return count;
    }

    //EFFECTS: returns deck as json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("deckName", deckName);
        json.put("cards", cardsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Card c : deck) {
            if (c instanceof PokemonCard) {
                jsonArray.put(c.toJson());
            } else if (c instanceof TrainerCard) {
                jsonArray.put(c.toJson());
            } else {
                jsonArray.put(c.toJson());
            }
        }

        return jsonArray;
    }
}
