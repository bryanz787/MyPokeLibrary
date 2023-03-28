package ui;

import java.io.FileNotFoundException;

//Main class, starts an instance of the collection app
public class Main {
    public static void main(String[] args) {
        try {
            new CollectionApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
