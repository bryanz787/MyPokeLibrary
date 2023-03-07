package persistance;

import model.Card;
import model.Collection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReaderCollection {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderCollection(String source) {
        this.source = source;
    }

    // EFFECTS: reads collection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Collection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Collection parseCollection(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Collection c = new Collection();
        addCards(c, jsonObject);
        return c;
    }

    // MODIFIES: c
    // EFFECTS: parses cards from JSON object and adds them to collection
    private void addCards(Collection c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(c, nextCard);
        }
    }

    // MODIFIES: c
    // EFFECTS: parses card from JSON object and adds it to collection
    private void addCard(Collection c, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String cardType = String.valueOf(jsonObject.getString("category"));
        Card card;

        if (cardType.equals("pokemon")) {
            card = addPokemon();
        } else if (cardType.equals("trainer")) {
            card = addTrainer();
        } else {
            card = addEnergy();
        }

        c.addCard(card);
    }

    //EFFECTS: parses pokemon card from JSON object and returns it
    private Card addPokemon() {
    }

    //EFFECTS: parses trainer card from JSON object and returns it
    private Card addTrainer() {
    }

    //EFFECTS: parses energy card from JSON object and returns it
    private Card addEnergy() {
    }
}
