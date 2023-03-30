package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//A series of tests for the Collection class in model
public class CollectionTest {
    Collection test;
    PokemonCard base;
    PokemonCard first;
    PokemonCard second;
    TrainerCard testTrainerA;
    TrainerCard testTrainerB;
    EnergyCard testEnergyA;
    EnergyCard testEnergyB;

    @BeforeEach
    public void setUp() {
        test = new Collection();
        base = new PokemonCard("base", "pi", false,
                50, 0);
        first = new PokemonCard("first", "pi", false,
                70, 1);
        second = new PokemonCard("second", "pi", true,
                120, 2);
        testTrainerA = new TrainerCard("testTrainer", false, "none");
        testTrainerB = new TrainerCard("testTrainer", true, "none");
        testEnergyA = new EnergyCard("r", false);
        testEnergyB = new EnergyCard("w", true);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, test.collectionSize());
    }

    @Test
    public void testAddCard() {
        assertEquals(0, test.collectionSize());
        test.addCard(base);
        test.addCard(first);
        assertEquals(2, test.collectionSize());
        assertEquals(base, test.getCard(0));
        assertEquals(first, test.getCard(1));
    }

    @Test
    public void testCountByCriteria() {
        for (int i = 0; i < 10; i++) {
            test.addCard(base);
            test.addCard(second);
            test.addCard(testEnergyA);
            test.addCard(testEnergyB);
            test.addCard(testTrainerB);
        }

        assertEquals(20 , test.countByCriteria("p"));
        assertEquals(10 , test.countByCriteria("t"));
        assertEquals(20 , test.countByCriteria("e"));
        assertEquals(30 , test.countByCriteria("h"));
        assertEquals(10 , test.countByCriteria("b"));
        assertEquals(0 , test.countByCriteria("f"));
        assertEquals(10 , test.countByCriteria("s"));

        test.addCard(first);
        test.addCard(second);
        test.addCard(testTrainerA);
        assertEquals(22 , test.countByCriteria("p"));
        assertEquals(11 , test.countByCriteria("t"));
        assertEquals(20 , test.countByCriteria("e"));
        assertEquals(31 , test.countByCriteria("h"));
        assertEquals(10 , test.countByCriteria("b"));
        assertEquals(1 , test.countByCriteria("f"));
        assertEquals(11 , test.countByCriteria("q"));

    }

    @Test
    public void testToJson() {
        for (int i = 0; i < 5; i++) {
            test.addCard(base);
            test.addCard(second);
            test.addCard(testEnergyA);
            test.addCard(testEnergyB);
            test.addCard(testTrainerB);
        }
        JSONArray testJ = test.toJson();

        Integer count = 0;
        for (Object j: testJ) {
            count++;
        }

        assertEquals(25, count);
        assertEquals("pokemon", testJ.getJSONObject(6).getString("cardType"));
        assertEquals("base", testJ.getJSONObject(5).getString("pokeName"));
    }
}
