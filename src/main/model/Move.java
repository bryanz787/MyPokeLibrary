package model;

// Class Move describes a specific move that a pokemon uses
public class Move {

    private String moveName;
    private int damage;
    private String moveEffects;

    //REQUIRES: MoveName.length(), Damage >= 0
    //EFFECTS: Creates a new class
    public Move(String moveName, int damage, String moveEffects) {
        this.moveName = moveName;
        this.damage = damage;
        this.moveEffects = moveEffects;
    }

    //EFFECTS: getter, returns the move name
    public String getMoveName() {
        return moveName;
    }

    //EFFECTS: getter, returns the moves damage
    public int getDamage() {
        return damage;
    }

    //EFFECTS: getter, returns the moves effects
    public String getMoveEffects() {
        return moveEffects;
    }
}
