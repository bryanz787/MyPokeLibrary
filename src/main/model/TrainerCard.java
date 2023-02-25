package model;

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
