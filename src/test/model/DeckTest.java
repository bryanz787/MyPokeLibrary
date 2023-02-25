package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    Deck test;
    PokemonCard base;
    PokemonCard first;
    PokemonCard second;
    TrainerCard testTrainerA;
    TrainerCard testTrainerB;
    EnergyCard testEnergyA;
    EnergyCard testEnergyB;

    @BeforeEach
    public void setUp() {
        test = new Deck("test");
        base = new PokemonCard("base", "pi", false,
                50, 0, new ArrayList<>());
        first = new PokemonCard("first", "pi", false,
                70, 1, new ArrayList<>());
        second = new PokemonCard("second", "pi", true,
                120, 2, new ArrayList<>());
        testTrainerA = new TrainerCard("testTrainer", false, "none");
        testTrainerB = new TrainerCard("testTrainer", true, "none");
        testEnergyA = new EnergyCard("r", false);
        testEnergyB = new EnergyCard("w", true);
    }

    @Test
    public void testConstructor() {
        assertEquals("test", test.getDeckName());
        assertEquals(0, test.deckSize());
        Deck testTwo = new Deck("test2");
        assertEquals("test2", testTwo.getDeckName());
    }

    @Test
    public void testAddCard() {
        assertEquals(0, test.deckSize());
        test.addCard(base);
        test.addCard(second);
        assertEquals(2, test.deckSize());
        assertEquals("second", test.getCard(1).getName());
    }

    @Test
    public void testLegalLength() {
        assertFalse(test.legalLength());

        test.addCard(base);
        assertFalse(test.legalLength());

        for (int i = 0; i < 59; i++) {
            test.addCard(testEnergyA);
        }
        assertTrue(test.legalLength());

        test.addCard(second);
        assertFalse(test.legalLength());
    }

    @Test
    public void testContainsBase() {
        assertFalse(test.containsBase());

        test.addCard(second);
        assertFalse(test.containsBase());

        test.addCard(testTrainerA);
        assertFalse(test.containsBase());

        test.addCard(testEnergyB);
        assertFalse(test.containsBase());

        test.addCard(base);
        assertTrue(test.containsBase());
    }

    @Test
    public void testLegalDuplicateCount() {
        assertTrue(test.legalDuplicateCount());

        for (int i = 0; i < 13; i ++) {
            test.addCard(testEnergyB);
        }
        assertTrue(test.legalDuplicateCount());

        for (int i = 0; i < 4; i ++) {
            test.addCard(testTrainerA);
        }
        assertTrue(test.legalDuplicateCount());

        test.addCard(testTrainerA);
        assertFalse(test.legalDuplicateCount());

        test = new Deck("test");

        for (int i = 0; i < 4; i ++) {
            test.addCard(base);
        }
        assertTrue(test.legalDuplicateCount());

        test.addCard(testTrainerA);
        assertTrue(test.legalDuplicateCount());

        test.addCard(second);
        assertTrue(test.legalDuplicateCount());

        test.addCard(base);
        assertFalse(test.legalDuplicateCount());
    }

    @Test
    public void testCountCard() {
        assertEquals(0,test.countCard().get(0).size());
        assertTrue(test.countCard().get(0).size() == test.countCard().get(1).size());

        for (int i = 0; i < 10; i ++) {
            test.addCard(testEnergyB);
        }
        assertEquals(0,test.countCard().get(0).size());

        test.addCard(base);
        assertEquals("base",test.countCard().get(0).get(0));
        assertEquals(1, test.countCard().get(1).get(0));

        test.addCard(base);
        assertEquals(1,test.countCard().get(0).size());
        assertEquals(2, test.countCard().get(1).get(0));

        for (int i = 0; i < 10; i ++) {
            test.addCard(testTrainerA);
            test.addCard(second);
            test.addCard(first);
        }
        test.addCard(second);
        test.addCard(second);
        test.addCard(testEnergyB);

        assertEquals(4,test.countCard().get(0).size());
        assertTrue(test.countCard().get(0).size() == test.countCard().get(1).size());
    }

    @Test
    public void testLegalDeck() {
        assertFalse(test.legalDeck());

        //no requirements satisfied
        for (int i = 0; i < 5; i ++) {
            test.addCard(first);
        }
        assertFalse(test.legalDeck());

        //card count satisfied
        for (int i = 0; i < 55; i ++) {
            test.addCard(first);
        }
        assertFalse(test.legalDeck());

        //has base card satisfied
        test = new Deck("test");
        for (int i = 0; i < 5; i ++) {
            test.addCard(base);
        }
        assertFalse(test.legalDeck());

        //has legal duplicates
        for (int i = 0; i < 4; i ++) {
            test.addCard(first);
        }
        assertFalse(test.legalDeck());

        //60 cards & has base
        test = new Deck("test");
        for (int i = 0; i < 60; i ++) {
            test.addCard(base);
        }
        assertFalse(test.legalDeck());

        //has base and duplicates
        test = new Deck("test");
        test.addCard(base);
        assertFalse(test.legalDeck());

        //60 cards and duplicates
        test = new Deck("test");
        for (int i = 0; i < 60; i ++) {
            test.addCard(testEnergyB);
        }
        assertFalse(test.legalDeck());

        //all valid, except 59 cards
        test = new Deck("test");
        for (int i = 0; i < 4; i ++) {
            test.addCard(base);
        }
        for (int i = 0; i < 55; i ++) {
            test.addCard(testEnergyB);
        }
        assertFalse(test.legalDeck());

        //all yes
        test.addCard(testEnergyB);
        assertTrue(test.legalDeck());

        //all yes, except 61 cards
        test.addCard(testTrainerA);
        assertFalse(test.legalDeck());
    }

    @Test
    public void testEnergyCount() {
        assertEquals(0, test.energyCount());

        for (int i = 0; i < 4; i ++) {
            test.addCard(base);
        }
        assertEquals(0, test.energyCount());

        for (int i = 0; i < 13; i ++) {
            test.addCard(testEnergyB);
        }
        assertEquals(13, test.energyCount());
    }
}
