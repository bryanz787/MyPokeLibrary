package persistance;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

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