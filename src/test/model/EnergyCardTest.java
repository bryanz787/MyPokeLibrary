package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//A series of tests for the EnergyCard class in model
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
        assertEquals("fire Energy", testEnergyR.getName());
        assertEquals("fighting Energy", testEnergyBr.getName());
        assertEquals("dragon Energy", testEnergyGo.getName());
        assertEquals("electric Energy", testEnergyY.getName());
        assertEquals("grass Energy", testEnergyGr.getName());
        assertEquals("water Energy", testEnergyBl.getName());
        assertEquals("fairy Energy", testEnergyPi.getName());
        assertEquals("psychic Energy", testEnergyPu.getName());
        assertEquals("dark Energy", testEnergyB.getName());
        assertEquals("steel Energy", testEnergyS.getName());
        assertEquals("colourless Energy", testEnergyW.getName());
        assertEquals("dragon Energy", testEnergyOther.getName());

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

    @Test
    public void testHoloString() {
        assertEquals("NON-HOLO", testEnergyS.holoString());
        assertEquals("HOLO", testEnergyW.holoString());
    }

    @Test
    public void testToString() {
        assertEquals("[NON-HOLO] steel Energy", testEnergyS.toString());
        assertEquals("[HOLO] colourless Energy", testEnergyW.toString());
    }
}
