package persistance;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
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
    public String readUsername() throws IOException{
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonObject.getString("name");
    }

    // EFFECTS: reads collection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Collection readCollection() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray("collection");
        return parseCollection(jsonArray);
    }

    // EFFECTS: parses collection from JSON object and returns it
    private Collection parseCollection(JSONArray jsonArray) {
        Collection c = new Collection();
        addCards(c, jsonArray);
        return c;
    }

    // MODIFIES: c
    // EFFECTS: parses cards from JSON object and adds them to collection
    private void addCards(Collection c, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCardCollection(c, nextCard);
        }
    }

    // MODIFIES: c
    // EFFECTS: parses card from JSON object and adds it to collection depending on card type
    private void addCardCollection(Collection c, JSONObject jsonObject) {
        String cardType = String.valueOf(jsonObject.getString("category"));
        Card card;

        if (cardType.equals("pokemon")) {
            card = addPokemon(jsonObject);
        } else if (cardType.equals("trainer")) {
            card = addTrainer(jsonObject);
        } else {
            card = addEnergy(jsonObject);
        }

        c.addCard(card);
    }

    //EFFECTS: parses pokemon card from JSON object and returns it
    private Card addPokemon(JSONObject jsonObject) {
        String pokeName = String.valueOf(jsonObject.getString("pokeName"));
        String pokeType = String.valueOf(jsonObject.getString("pokeType"));
        Boolean holofoil = Boolean.valueOf(jsonObject.getString("holofoil"));
        Integer hitPoints = Integer.valueOf(jsonObject.getString("hitPoints"));
        Integer stage = Integer.valueOf(jsonObject.getString("stage"));

        return new PokemonCard(pokeName, pokeType, holofoil, hitPoints, stage);
    }


    //EFFECTS: parses trainer card from JSON object and returns it
    private Card addTrainer(JSONObject jsonObject) {
        String cardName = String.valueOf(jsonObject.getString("pokeName"));
        Boolean holofoil = Boolean.valueOf(jsonObject.getString("holofoil"));
        String effects = String.valueOf(jsonObject.getString("effects"));

        return new TrainerCard(cardName, holofoil, effects);
    }

    //EFFECTS: parses energy card from JSON object and returns it
    private Card addEnergy(JSONObject jsonObject) {
        String type = String.valueOf(jsonObject.getString("type"));
        Boolean holofoil = Boolean.valueOf(jsonObject.getString("holofoil"));

        return new EnergyCard(type, holofoil);
    }

    // EFFECTS: reads decksList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public List<Deck> readDeckList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONArray jsonArray = jsonObject.getJSONArray("deckList");
        return parseDeckList(jsonArray);
    }

    // EFFECTS: parses decks list from JSON object and returns it
    private List<Deck> parseDeckList(JSONArray jsonArray) {
        List<Deck> dl = new ArrayList<>();
        addDecks(dl, jsonArray);
        return dl;
    }

    // MODIFIES: dl
    // EFFECTS: parses deck list and adds decks to deck list
    private void addDecks(List<Deck> dl, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONArray nextDeck = (JSONArray) json;
            addDeck(dl, nextDeck);
        }
    }

    // MODIFIES: dl
    // EFFECTS: parses card from JSON object and adds it to deck then adds deck to dl
    private void addDeck(List<Deck> dl, JSONArray jsonArray) {
        Deck deck = new Deck(jsonArray.getString(0));
        Object cardArray = jsonArray.get(1);
        for (Object json : (JSONArray) cardArray) {
            JSONObject nextCard = (JSONObject) json;
            addCardDeck(deck , nextCard);
        }
        dl.add(deck);

    }

    //MODIFIES: deck
    //EFFECTS: parses card from JSON object and adds card to deck
    private void addCardDeck(Deck deck, JSONObject jsonObject) {
        String cardType = String.valueOf(jsonObject.getString("category"));
        Card card;

        if (cardType.equals("pokemon")) {
            card = addPokemon(jsonObject);
        } else if (cardType.equals("trainer")) {
            card = addTrainer(jsonObject);
        } else {
            card = addEnergy(jsonObject);
        }

        deck.addCard(card);
    }


}
