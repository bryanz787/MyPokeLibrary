package persistance;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.CollectionApp;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//A series of tests for the JSONReader class in persistance
public class JsonReaderTest {
    private Collection cardsList;
    private List<Deck> deckList;
    private String userName;

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            cardsList = reader.readCollection();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyFile() {
        JsonReader reader = new JsonReader("./data/emptyTest.json");
        try {
            cardsList = reader.readCollection();
            deckList = reader.readDeckList();
            userName = reader.readUsername();
            assertEquals("bryan", userName);
            assertEquals(0, cardsList.collectionSize());
            assertEquals(0, deckList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testFile.json");
        try {
            assertEquals("bryan", reader.readUsername());
            assertEquals(10, reader.readCollection().collectionSize());
            assertEquals(1, reader.readDeckList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testCollectionExplicit() {
        try {
            Collection test = new Collection();
            ;
            PokemonCard base = new PokemonCard("base", "pi", false,
                    50, 0);
            TrainerCard testTrainerA = new TrainerCard("testTrainer", false, "none");
            EnergyCard testEnergyA = new EnergyCard("r", false);

            for (int i = 0; i < 3; i++) {
                test.addCard(base);
                test.addCard(testTrainerA);
                test.addCard(testEnergyA);
            }

            JsonWriter writer = new JsonWriter("./data/testFileA.json");
            JSONObject json = new JSONObject();
            JSONArray jsonArray = test.toJson();
            json.put("cardsList", jsonArray);
            writer.open();
            writer.write(json);
            writer.close();
            JsonReader reader = new JsonReader("./data/testFileA.json");
            Collection testRead = reader.readCollection();
            assertEquals(9, testRead.collectionSize());
            assertEquals("testTrainer", testRead.getCard(4).getName());
            assertFalse(testRead.getCard(2).getHolofoil());
        } catch (IOException e) {
            fail("No exception should be thrown here");
        }
    }
}
