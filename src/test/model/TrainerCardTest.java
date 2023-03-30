package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//A series of tests for the TrainerCard class in model
public class TrainerCardTest {
    TrainerCard testTrainerA;
    TrainerCard testTrainerB;

    @BeforeEach
    public void setup() {
        testTrainerA = new TrainerCard("testTrainerA", false, "draw 3 cards from deck");
        testTrainerB = new TrainerCard("testTrainerB", true, "none");
    }

    @Test
    public void testConstructor() {
        assertEquals("testTrainerA", testTrainerA.getName());
        assertEquals("testTrainerB", testTrainerB.getName());

        assertFalse(testTrainerA.getHolofoil());
        assertTrue(testTrainerB.getHolofoil());

        assertEquals("draw 3 cards from deck", testTrainerA.getEffects());
        assertEquals("none", testTrainerB.getEffects());
    }

    @Test
    public void testToJson() {
        JSONObject testJ = testTrainerA.toJson();
        assertEquals("testTrainerA", testJ.get("cardName"));
        assertEquals(false, testJ.get("holofoil"));
    }

    @Test
    public void testHoloString() {
        assertEquals("NON-HOLO", testTrainerA.holoString());
        assertEquals("HOLO", testTrainerB.holoString());
    }

    @Test
    public void testToString() {
        assertEquals("[NON-HOLO] testTrainerA | EFFECTS: draw 3 cards from deck", testTrainerA.toString());
        assertEquals("[HOLO] testTrainerB | EFFECTS: none", testTrainerB.toString());
    }
}
