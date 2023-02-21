package model;

//Represents an energy card in a given collection, has attributes needed to describe the card
public class EnergyCard extends Card{

    private String type;
    private Boolean holofoil;

    //EFFECTS: Constructor for energy card class
    public EnergyCard(String type, Boolean holofoil) {
        this.type = type;
        this.holofoil = holofoil;

        confirmCreation(this.type + " energy");
    }

    //EFFECTS: Getter for energy cards type
    public String getType() {
        return type;
    }

    //EFFECTS: Getter for energy cards holofoil status
    public Boolean getHolofoil() {
        return holofoil;
    }
}
