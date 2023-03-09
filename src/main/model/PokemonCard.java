package model;

import org.json.JSONObject;
import persistance.Writable;

import java.util.List;

//Represents a pokemon card in a given collection, has attributes needed to describe the card
public class PokemonCard extends Card {

    private String pokeName;
    private String pokeType;
    private boolean holofoil;
    private int hitPoints;
    private int stage;

    //REQUIRES: Hitpoints > 0, Stage in [0, 1, 2]
    //EFFECTS: Constructor for PokemonCard class
    public PokemonCard(String pokeName, String pokeType, Boolean holofoil, int hitPoints, int stage) {
        this.pokeName = pokeName;
        this.pokeType = convertToType(pokeType);
        this.holofoil = holofoil;
        this.hitPoints = hitPoints;
        this.stage = stage;
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
        } else if (type.equals("w")) {
            return "colourless";
        } else {
            return type;
        }
    }

    //EFFECTS: returns card as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cardType", "pokemon");
        json.put("pokeName", pokeName);
        json.put("pokeType", pokeType);
        json.put("holofoil", holofoil);
        json.put("hitpoints", hitPoints);
        json.put("stage", stage);
        return json;
    }

    //EFFECTS: Getter for the Pokemon cards name field
    @Override
    public String getName() {
        return pokeName;
    }

    //EFFECTS: Getter for the Pokemon cards type field
    public String getPokeType() {
        return pokeType;
    }

    //EFFECTS: retrieves the cards holo status
    @Override
    public Boolean getHolofoil() {
        return holofoil;
    }

    //EFFECTS: Getter for the Pokemon cards hitpoints field
    public int getHitPoints() {
        return hitPoints;
    }

    //EFFECTS: Getter for the Pokemon cards stage field
    public int getStage() {
        return stage;
    }
}
