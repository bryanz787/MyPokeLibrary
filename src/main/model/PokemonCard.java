package model;

import java.util.List;

//Represents a pokemon card in a given collection, has attributes needed to describe the card
public class PokemonCard extends Card{

    private String pokeName;
    private String pokeType;
    private Boolean holofoil;
    private int hitPoints;
    private int Stage;
    private List<Move> moves;

    //REQUIRES: Hitpoints > 0, Stage in [1,2,3]
    //EFFECTS: Constructor for PokemonCard class
    public PokemonCard(String pokeName, String pokeType, Boolean holofoil, int hitPoints, int Stage, List<Move> moves) {
        this.pokeName = pokeName;
        this.pokeType = pokeType;
        this.holofoil = holofoil;
        this.hitPoints = hitPoints;

        //Create a new method that uses the Move constructor and adds the moves list to the moves field

        confirmCreation(this.pokeName);
    }

    //EFFECTS: Getter for the Pokemon cards name field
    public String getPokeName() {
        return pokeName;
    }

    //EFFECTS: Getter for the Pokemon cards type field
    public String getPokeType() {
        return pokeType;
    }

    //EFFECTS: Getter for the holofoil status field
    public Boolean getHolofoil() {
        return holofoil;
    }

    //EFFECTS: Getter for the Pokemon cards hitpoints field
    public int getHitPoints() {
        return hitPoints;
    }

    //EFFECTS: Getter for the Pokemon cards stage field
    public int getStage() {
        return Stage;
    }

    //EFFECTS: Getter for the moves field
    public List<Move> getMoves() {
        return moves;
    }
}
