package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnergyCardTest {
    EnergyCard testEnergyR;
    EnergyCard testEnergyBr;
    EnergyCard testEnergyGo;
    EnergyCard testEnergyY;
    EnergyCard testEnergyGr;
    EnergyCard testEnergyBl;
    EnergyCard testEnergyPi;
    EnergyCard testEnergyPu;
    EnergyCard testEnergyB;
    EnergyCard testEnergyS;
    EnergyCard testEnergyW;
    EnergyCard testEnergyOther;

    @BeforeEach
    public void setup() {
        testEnergyR = new EnergyCard("r", false);
        testEnergyBr = new EnergyCard("br", false);
        testEnergyGo = new EnergyCard("go", false);
        testEnergyY = new EnergyCard("y", false);
        testEnergyGr = new EnergyCard("gr", false);
        testEnergyBl = new EnergyCard("bl", false);
        testEnergyPi = new EnergyCard("pi", false);
        testEnergyPu = new EnergyCard("pu", false);
        testEnergyB = new EnergyCard("b", false);
        testEnergyS = new EnergyCard("s", false);
        testEnergyW = new EnergyCard("w", true);
        testEnergyOther = new EnergyCard("dragon", false);
    }

    @Test
    public void testConstructor() {
        assertEquals("fire energy", testEnergyR.getName());
        assertEquals("fighting energy", testEnergyBr.getName());
        assertEquals("dragon energy", testEnergyGo.getName());
        assertEquals("electric energy", testEnergyY.getName());
        assertEquals("grass energy", testEnergyGr.getName());
        assertEquals("water energy", testEnergyBl.getName());
        assertEquals("fairy energy", testEnergyPi.getName());
        assertEquals("psychic energy", testEnergyPu.getName());
        assertEquals("dark energy", testEnergyB.getName());
        assertEquals("steel energy", testEnergyS.getName());
        assertEquals("colourless energy", testEnergyW.getName());
        assertEquals("dragon energy", testEnergyOther.getName());

        assertFalse(testEnergyBl.getHolofoil());
        assertTrue(testEnergyW.getHolofoil());

        assertEquals("colourless", testEnergyW.getType());
        assertEquals("electric", testEnergyY.getType());
    }

    @Test
    public void testToJson() {
        JSONObject testJ = testEnergyBr.toJson();
        assertEquals("fighting", testJ.get("type"));
        assertEquals(false, testJ.get("holofoil"));
    }
}
