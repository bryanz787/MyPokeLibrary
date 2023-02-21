package model;

public abstract class Card {
    //EFFECTS: Confirms to the user that their card has been created and added
    public void confirmCreation(String cardName) {
        System.out.println("Success, your " + cardName + " card has been added!");
    }
}
