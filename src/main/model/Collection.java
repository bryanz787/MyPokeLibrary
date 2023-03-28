package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
import java.util.List;

//A class representing a persons pokemon card collection
public class Collection implements Writable {
    private List<Card> collection;


    //EFFECTS: A constructor of a collection of pokemon cards
    public Collection() {
        collection = new ArrayList<Card>();
    }

    //MODIFIES: this
    //EFFECTS: adds a given card to a collection
    public void addCard(Card toAdd) {
        collection.add(toAdd);
    }

    //EFFECTS: returns the amount of cards in a collection
    public int collectionSize() {
        return collection.size();
    }

    //EFFECTS: returns the card at a given index
    public Card getCard(int index) {
        return collection.get(index);
    }

    //EFFECTS: returns the number of a certain card type in a collection based off criteria
    public int countByCriteria(String command) {
        int count = 0;

        if (command.equals("p")) {
            count = numPokemon();
        } else if (command.equals("t")) {
            count = numTrainer();
        } else if (command.equals("e")) {
            count = numEnergy();
        } else if (command.equals("h")) {
            count = numHolo();
        } else if (command.equals("b")) {
            count = numBase();
        } else if (command.equals("f")) {
            count = numFirst();
        } else { // if (command.equals("s")) 
            count = numSecond();
        }

        return count;
    }

    //EFFECTS: returns the number of pokemon cards in the collection
    private int numPokemon() {
        int count = 0;
        for (Card c : collection) {
            if (c instanceof PokemonCard) {
                count++;
            }
        }
        return count;
    }

    //EFFECTS: returns the number of trainer cards in the collection
    private int numTrainer() {
        int count = 0;
        for (Card c : collection) {
            if (c instanceof TrainerCard) {
                count++;
            }
        }
        return count;
    }

    //EFFECTS: returns the number of energy cards in the collection
    private int numEnergy() {
        int count = 0;
        for (Card c : collection) {
            if (c instanceof EnergyCard) {
                count++;
            }
        }
        return count;
    }

    //EFFECTS: returns the number of holo cards in the collection
    private int numHolo() {
        int count = 0;
        for (Card c : collection) {
            if (c.getHolofoil()) {
                count++;
            }
        }
        return count;
    }

    //EFFECTS: returns the number of base stage cards in the collection
    private int numBase() {
        int count = 0;
        for (Card c : collection) {
            if (c instanceof PokemonCard) {
                if (((PokemonCard) c).getStage() == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    //EFFECTS: returns the number of first evolution stage cards in the collection
    private int numFirst() {
        int count = 0;
        for (Card c : collection) {
            if (c instanceof PokemonCard) {
                if (((PokemonCard) c).getStage() == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    //EFFECTS: returns the number of second evolution stage cards in the collection
    private int numSecond() {
        int count = 0;
        for (Card c : collection) {
            if (c instanceof PokemonCard) {
                if (((PokemonCard) c).getStage() == 2) {
                    count++;
                }
            }
        }
        return count;
    }

    //EFFECTS: returns collection as json array
    @Override
    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();

        for (Card c : collection) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
