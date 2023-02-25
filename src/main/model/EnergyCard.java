package model;

//Represents an energy card in a given collection, has attributes needed to describe the card
public class EnergyCard extends Card {

    private String type;
    private boolean holofoil;

    //EFFECTS: Constructor for energy card class
    public EnergyCard(String type, Boolean holofoil) {
        this.type = convertToType(type);
        this.holofoil = holofoil;
    }

    //EFFECTS: returns full type name given abbreviation, defaults to colourless
    private String convertToType(String type) {
        if (type.equals("r")) {
            return "fire";
        } else if (type.equals("br")) {
            return "fighting";
        } else if (type.equals("go")) {
            return "dragon";
        } else if (type.equals("y")) {
            return "electric";
        } else if (type.equals("gr")) {
            return "grass";
        } else if (type.equals("bl")) {
            return "water";
        } else if (type.equals("pi")) {
            return "fairy";
        } else if (type.equals("pu")) {
            return "psychic";
        } else if (type.equals("b")) {
            return "dark";
        } else if (type.equals("s")) {
            return "steel";
        } else {
            return "colourless";
        }
    }

    //EFFECTS: Getter for energy cards type
    public String getType() {
        return type;
    }

    //EFFECTS: retrieves the cards holo status
    @Override
    public Boolean getHolofoil() {
        return holofoil;
    }

    //EFFECTS: Gets energy cards name
    @Override
    public String getName() {
        return getType() + " energy";
    }
}
