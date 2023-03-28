package model;

import org.json.JSONObject;
import persistance.Writable;

//An abstract class that is the supertype of the various types of cards
public abstract class Card implements Writable {
    //EFFECTS: retrieves the cards name
    public abstract String getName();

    //EFFECTS: retrieves the cards holo status
    public abstract Boolean getHolofoil();

    public abstract JSONObject toJson();

}
