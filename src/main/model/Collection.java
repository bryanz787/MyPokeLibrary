package model;

import java.util.ArrayList;
import java.util.List;

//A class representing a person and their pokemon card collection
public class Collection {

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

    //EFFECTS: returns the cards in a collection
    public List<Card> getCardList() {
        return collection;
    }
}
