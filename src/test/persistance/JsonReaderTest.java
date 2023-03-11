package persistance;

import model.Collection;
import model.Deck;
import org.junit.jupiter.api.Test;
import ui.CollectionApp;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    private Collection cardsList;
    private List<Deck> deckList;
    private String userName;
    private CollectionApp testApp;

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            cardsList = reader.readCollection();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFile() {
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
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testFile.json");
        try {
            assertEquals("bryan", reader.readUsername());
            assertEquals(10, reader.readCollection().collectionSize());
            assertEquals(1, reader.readDeckList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
