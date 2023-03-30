package model;

import org.json.JSONObject;
import persistance.Writable;

//Represents a trainer card in a given collection, has attributes needed to describe the card
public class TrainerCard extends Card {

    private String cardName;
    private boolean holofoil;
    private String effects;

    //REQUIRES: Effects.length() > 0
    //EFFECTS: Constructor for trainer card class
    public TrainerCard(String cardName, Boolean holofoil, String effects) {
        this.cardName = cardName;
        this.holofoil = holofoil;
        this.effects = effects;
    }

    //EFFECTS: returns card as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cardType", "trainer");
        json.put("cardName", cardName);
        json.put("holofoil", holofoil);
        json.put("effects", effects);
        return json;
    }

    //EFFECTS: Returns the representation of a trainer card as a string
    @Override
    public String toString() {
        return "[" + holoString() + "] " + cardName + " | EFFECTS: " + effects;
    }

    //EFFECTS: returns string representation of whether a card is holographic or not
    public String holoString() {
        return holofoil ? "HOLO" : "NON-HOLO";
    }

    //EFFECTS: Getter for trainer cards name
    @Override
    public String getName() {
        return cardName;
    }

    //EFFECTS: retrieves the cards holo status
    @Override
    public Boolean getHolofoil() {
        return holofoil;
    }

    //EFFECTS: Getter for trainer cards effects
    public String getEffects() {
        return effects;
    }

}
