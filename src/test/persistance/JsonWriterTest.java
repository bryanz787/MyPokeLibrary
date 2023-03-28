package persistance;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


//A series of tests for the JSONWriter class in persistance
class JsonWriterTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            String userName = "bryan";
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterSingleField() {
        try {
            String userName = "Bryan";
            JsonWriter writer = new JsonWriter("./data/testSingle.json");
            JSONObject json = new JSONObject();
            json.put("userName", userName);
            writer.open();
            writer.write(json);
            writer.close();

            JsonReader reader = new JsonReader("./data/testSingle.json");
            userName = reader.readString();
            assertEquals("Bryan", userName);

            userName = "George";
            json.put("userName", userName);
            writer.open();
            writer.write(json);
            writer.close();

            reader = new JsonReader("./data/testSingle.json");
            userName = reader.readString();
            assertEquals("George", userName);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}