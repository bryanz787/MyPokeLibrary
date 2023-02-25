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

    //EFFECTS: returns full type name given abbreviation
    @SuppressWarnings("methodlength")
    private String convertToType(String type) {
        String toReturn = "";

        if (type.equals("r")) {
            toReturn = "fire";
        } else if (type.equals("br")) {
            toReturn = "fighting";
        } else if (type.equals("go")) {
            toReturn = "dragon";
        } else if (type.equals("y")) {
            toReturn = "electric";
        } else if (type.equals("gr")) {
            toReturn = "grass";
        } else if (type.equals("bl")) {
            toReturn = "water";
        } else if (type.equals("pi")) {
            toReturn = "fairy";
        } else if (type.equals("pu")) {
            toReturn = "psychic";
        } else if (type.equals("b")) {
            toReturn = "dark";
        } else if (type.equals("s")) {
            toReturn = "steel";
        } else if (type.equals("w")) {
            toReturn = "colourless";
        }

        return toReturn;
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
