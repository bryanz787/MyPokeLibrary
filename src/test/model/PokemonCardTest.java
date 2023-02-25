package model;

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
    Move testMoveA;
    Move testMoveB;

    @BeforeEach
    public void setup() {
        testMoveA = new Move("test move a", 100, "no additional effects");
        testMoveB = new Move("test move b", 0, "poison the opponents pokemon");

        testPokeR = new PokemonCard("fire", "r",false,
                100, 0, new ArrayList<Move>(Arrays.asList(testMoveA, testMoveB)));
        testPokeBr = new PokemonCard("fighting", "br",true,
                130, 1, new ArrayList<Move>());
        testPokeGo = new PokemonCard("dragon", "go",false,
                100, 1, new ArrayList<Move>(Arrays.asList(testMoveA, testMoveB)));
        testPokeY = new PokemonCard("electric", "y",false,
                40, 1, new ArrayList<Move>(Arrays.asList(testMoveB)));
        testPokeGr = new PokemonCard("grass", "gr",true,
                100, 2, new ArrayList<Move>(Arrays.asList(testMoveA, testMoveB)));
        testPokeBl = new PokemonCard("water", "bl",false,
                110, 1, new ArrayList<Move>(Arrays.asList(testMoveA)));
        testPokePi = new PokemonCard("fairy", "pi",false,
                120, 2, new ArrayList<Move>());
        testPokePu = new PokemonCard("psychic", "pu",true,
                70, 0, new ArrayList<Move>());
        testPokeB = new PokemonCard("dark", "b",false,
                100, 0, new ArrayList<Move>(Arrays.asList(testMoveB)));
        testPokeS = new PokemonCard("steel", "s",false,
                90, 1, new ArrayList<Move>(Arrays.asList(testMoveA)));
        testPokeW = new PokemonCard("colourless", "w",true,
                30, 2, new ArrayList<Move>(Arrays.asList(testMoveA, testMoveB)));

    }

    @Test
    public void testConstructor() {
        assertEquals("fire", testPokeR.getName());
        assertEquals("fighting", testPokeBr.getName());

        assertEquals("dragon", testPokeGo.getPokeType());
        assertEquals("electric", testPokeY.getPokeType());

        assertTrue(testPokeGr.getHolofoil());
        assertFalse(testPokeBl.getHolofoil());

        assertEquals(120, testPokePi.getHitPoints());
        assertEquals(70, testPokePu.getHitPoints());

        assertEquals(0, testPokeB.getStage());
        assertEquals(1, testPokeS.getStage());
        assertEquals(2, testPokeW.getStage());

        assertEquals(new ArrayList<Move>(), testPokeBr.getMoves());
        assertEquals(new ArrayList<Move>(Arrays.asList(testMoveA)), testPokeS.getMoves());
        assertEquals(new ArrayList<Move>(Arrays.asList(testMoveB)), testPokeB.getMoves());
        assertEquals(new ArrayList<Move>(Arrays.asList(testMoveA, testMoveB)), testPokeW.getMoves());
    }

}
