package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTest {
    Move testMoveA;
    Move testMoveB;

    @BeforeEach
    public void setup() {
        testMoveA = new Move("test move a", 100, "no additional effects");
        testMoveB = new Move("test move b", 0, "poison the opponents pokemon");
    }

    @Test
    public void testConstructor() {
        assertEquals("test move a", testMoveA.getMoveName());
        assertEquals("test move b", testMoveB.getMoveName());

        assertEquals(100, testMoveA.getDamage());
        assertEquals(0, testMoveB.getDamage());

        assertEquals("no additional effects", testMoveA.getMoveEffects());
        assertEquals("poison the opponents pokemon", testMoveB.getMoveEffects());
    }
}
