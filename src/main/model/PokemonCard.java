package model;

import java.util.List;

//Represents a pokemon card in a given collection, has attributes needed to describe the card
public class PokemonCard extends Card {

    private String pokeName;
    private String pokeType;
    private boolean holofoil;
    private int hitPoints;
    private int stage;
    private List<Move> moves;

    //REQUIRES: Hitpoints > 0, Stage in [0, 1, 2]
    //EFFECTS: Constructor for PokemonCard class
    public PokemonCard(String pokeName, String pokeType, Boolean holofoil, int hitPoints, int stage, List<Move> moves) {
        this.pokeName = pokeName;
        this.pokeType = convertToType(pokeType);
        this.holofoil = holofoil;
        this.hitPoints = hitPoints;
        this.stage = stage;
        this.moves = moves;
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

    //EFFECTS: Getter for the moves field
    public List<Move> getMoves() {
        return moves;
    }
}
