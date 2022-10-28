package persistence;

import model.Coord2D;
import model.Finger;
import model.KeyboardGeometry;
import model.Layout;
import model.corpora.StringCorpus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * Represents a reader that can parse relevant types from their JSONObject.
 * Structure from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
 */
public class Reader {

    private String fileLocation;
    private boolean hasBeenRead;
    private String fileContent;

    // EFFECTS: constructs an open Reader which reads from a given location
    public Reader(String location) {
        this.fileLocation = location;
        this.hasBeenRead = false;
        this.fileContent = "";
    }

    // REQUIRES: !hasBeenRead
    // MODIFIES: this
    // EFFECTS: reads the file associated with the Reader, closes the Reader (prevent future reads) and
    //          sets fileContent to the read content
    // Implementation from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java#L33
    private void readFile() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(fileLocation), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        fileContent = contentBuilder.toString();
        hasBeenRead = true;
    }

    // REQUIRES: hasBeenRead
    // EFFECTS: takes the file content and converts it into a JSONObject and returns it
    private JSONObject parseJson() {
        return new JSONObject(fileContent);
    }

    // MODIFIES: kg
    // EFFECTS: parses all the keys in the JSONArray and puts them into the geometry
    private void putKeys(KeyboardGeometry kg, JSONArray keys, int numContactPoints) {
        for (int keyIndex = 0; keyIndex < numContactPoints; keyIndex++) {
            JSONObject key = keys.getJSONObject(keyIndex);

            double keyX = key.getDouble("x");
            double keyY = key.getDouble("y");
            Finger keyFinger = Finger.fromFingerIndex(key.getInt("finger"));

            kg.withContactPoint(keyX, keyY, keyFinger);
        }
    }

    // MODIFIES: kg
    // EFFECTS: parses all the finger positions in the JSONArray and sets it as an initial finger position
    private void putFingerPositions(KeyboardGeometry kg, JSONArray fingerPositions) {
        for (int fingerIndex = 0; fingerIndex < 10; fingerIndex++) {
            JSONObject fingerPosition = fingerPositions.getJSONObject(fingerIndex);

            double fingerX = fingerPosition.getDouble("x");
            double fingerY = fingerPosition.getDouble("y");

            kg.setInitialFingerPosition(Finger.fromFingerIndex(fingerIndex), fingerX, fingerY);
        }
    }

    // EFFECTS: parses a KeyboardGeometry from the given JSONObject
    //          throws an InvalidParseError if you try to read a JSONObject that's not a KeyboardGeometry
    private KeyboardGeometry parseKeyboardGeometry(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("name");
        String desc = jsonObject.getString("description");
        int numContactPoints = jsonObject.getInt("numContactPoints");
        JSONArray keys = jsonObject.getJSONArray("keys");
        JSONArray fingerPositions = jsonObject.getJSONArray("initialFingerPositions");

        KeyboardGeometry parsedKg = new KeyboardGeometry(name, desc);

        putKeys(parsedKg, keys, numContactPoints);
        putFingerPositions(parsedKg, fingerPositions);

        return parsedKg;
    }

    // EFFECTS: parses a Layout from the given JSONObject
    //          throws an InvalidParseError if you try to read a JSONObject that's not a Layout
    private Layout parseLayout(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("name");
        String desc = jsonObject.getString("description");
        String layoutString = jsonObject.getString("layout");

        return new Layout(name, desc, layoutString);
    }

    // EFFECTS: parses a Coord2D from the given JSONObject
    //          throws an InvalidParseError if you try to read a JSONObject that's not a Coord2D
    private Coord2D parseCoord2D(JSONObject jsonObject) throws JSONException {
        double x = jsonObject.getDouble("x");
        double y = jsonObject.getDouble("y");

        return new Coord2D(x, y);
    }

    // EFFECTS: parses a StringCorpus from the given JSONObject
    //          throws an InvalidParseError if you try to read a JSONObject that's not a StringCorpus
    private StringCorpus parseStringCorpus(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("name");
        String desc = jsonObject.getString("description");
        String content = jsonObject.getString("content");

        return new StringCorpus(name, desc, content);
    }

    // REQUIRES: !hasBeenRead
    // MODIFIES: this
    // EFFECTS: reads a KeyboardGeometry from file and returns it
    //          throws IOException if there was an error in reading the file
    //          throws an InvalidParseError if you try to read a JSONObject that's not a KeyboardGeometry
    public KeyboardGeometry readKeyboardGeometry() throws IOException, JSONException {
        readFile();
        return parseKeyboardGeometry(parseJson());
    }

    // REQUIRES: !hasBeenRead
    // MODIFIES: this
    // EFFECTS: reads a Coord2D from file and returns it
    //          throws IOException if there was an error in reading the file
    //          throws an InvalidParseError if you try to read a JSONObject that's not a Coord2D
    public Coord2D readCoord2D() throws IOException, JSONException {
        readFile();
        return parseCoord2D(parseJson());
    }

    // REQUIRES: !hasBeenRead
    // MODIFIES: this
    // EFFECTS: reads a Layout from file and returns it
    //          throws IOException if there was an error in reading the file
    //          throws an InvalidParseError if you try to read a JSONObject that's not a Layout
    public Layout readLayout() throws IOException, JSONException {
        readFile();
        return parseLayout(parseJson());
    }

    // REQUIRES: !hasBeenRead
    // MODIFIES: this
    // EFFECTS: reads a StringCorpus from file and returns it
    //          throws IOException if there was an error in reading the file
    //          throws an InvalidParseError if you try to read a JSONObject that's not a StringCorpus
    public StringCorpus readStringCorpus() throws IOException, JSONException {
        readFile();
        return parseStringCorpus(parseJson());
    }

}
