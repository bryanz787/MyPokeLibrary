package model;

//Represents a trainer card in a given collection, has attributes needed to describe the card
public class TrainerCard extends Card{

    private String cardName;
    private Boolean holofoil;
    private String effects;

    //REQUIRES: Effects.length() > 0
    //EFFECTS: Constructor for trainer card class
    public TrainerCard(String cardName, Boolean holofoil, String effects) {
        this.cardName = cardName;
        this.holofoil = holofoil;
        this.effects = effects;

        confirmCreation(this.cardName);
    }

    //EFFECTS: Getter for trainer cards name
    public String getCardName() {
        return cardName;
    }

    //EFFECTS: Getter for trainer cards holofoil status
    public Boolean getHolofoil() {
        return holofoil;
    }

    //EFFECTS: Getter for trainer cards effects
    public String getEffects() {
        return effects;
    }

}
