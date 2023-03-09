package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonCardTest {

    PokemonCard testPokeR;
    PokemonCard testPokeBr;
    PokemonCard testPokeGo;
    PokemonCard testPokeY;
    PokemonCard testPokeGr;
    PokemonCard testPokeBl;
    PokemonCard testPokePi;
    PokemonCard testPokePu;
    PokemonCard testPokeB;
    PokemonCard testPokeS;
    PokemonCard testPokeW;
    PokemonCard testPokeOther;

    @BeforeEach
    public void setup() {
        testPokeR = new PokemonCard("fire", "r",false,
                100, 0);
        testPokeBr = new PokemonCard("fighting", "br",true,
                130, 1);
        testPokeGo = new PokemonCard("dragon", "go",false,
                100, 1);
        testPokeY = new PokemonCard("electric", "y",false,
                40, 1);
        testPokeGr = new PokemonCard("grass", "gr",true,
                100, 2);
        testPokeBl = new PokemonCard("water", "bl",false,
                110, 1);
        testPokePi = new PokemonCard("fairy", "pi",false,
                120, 2);
        testPokePu = new PokemonCard("psychic", "pu",true,
                70, 0);
        testPokeB = new PokemonCard("dark", "b",false,
                100, 0);
        testPokeS = new PokemonCard("steel", "s",false,
                90, 1);
        testPokeW = new PokemonCard("colourless", "w",true,
                30, 2);
        testPokeOther = new PokemonCard("fire", "fire",true,
                30, 2);
    }

    @Test
    public void testConstructor() {
        assertEquals("fire", testPokeR.getName());
        assertEquals("fighting", testPokeBr.getName());

        assertEquals("dragon", testPokeGo.getPokeType());
        assertEquals("electric", testPokeY.getPokeType());
        assertEquals("fire", testPokeOther.getPokeType());

        assertTrue(testPokeGr.getHolofoil());
        assertFalse(testPokeBl.getHolofoil());

        assertEquals(120, testPokePi.getHitPoints());
        assertEquals(70, testPokePu.getHitPoints());

        assertEquals(0, testPokeB.getStage());
        assertEquals(1, testPokeS.getStage());
        assertEquals(2, testPokeW.getStage());
    }

    @Test
    public void testToJson() {
        JSONObject testJ = testPokeR.toJson();
        assertEquals("fire", testJ.get("pokeName"));
        assertEquals(0, testJ.get("stage"));

    }

}
