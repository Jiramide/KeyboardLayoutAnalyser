package persistence;

import model.Coord2D;
import model.KeyboardGeometry;
import model.Layout;
import model.corpora.StringCorpus;

import java.io.IOException;
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

    }

    // REQUIRES: !hasBeenRead
    // MODIFIES: this
    // EFFECTS: reads the file associated with the Reader, closes the Reader (prevent future reads) and
    //          sets fileContent to the read content. Returns fileContent.
    private String readFile() throws IOException {
        return "";
    }

    // REQUIRES: hasBeenRead
    // EFFECTS: takes the file content and converts it into a JSONObject and returns it
    private JSONObject parseJson() {
        return null;
    }

    // EFFECTS: parses a KeyboardGeometry from the given JSONObject
    private KeyboardGeometry parseKeyboardGeometry(JSONObject jsonObject) {
        return null;
    }

    // EFFECTS: parses a Layout from the given JSONObject
    private Layout parseLayout(JSONObject jsonObject) {
        return null;
    }

    // EFFECTS: parses a Coord2D from the given JSONObject
    private Coord2D parseCoord2D(JSONObject jsonObject) {
        return null;
    }

    // EFFECTS: parses a StringCorpus from the given JSONObject
    private StringCorpus parseStringCorpus(JSONObject jsonObject) {
        return null;
    }

    // REQUIRES: !hasBeenRead
    // MODIFIES: this
    // EFFECTS: reads a KeyboardGeometry from file and returns it
    //          throws IOException if there was an error in reading the file
    public KeyboardGeometry readKeyboardGeometry() throws IOException {
        return null;
    }

    // REQUIRES: !hasBeenRead
    // MODIFIES: this
    // EFFECTS: reads a Coord2D from file and returns it
    //          throws IOException if there was an error in reading the file
    public Coord2D readCoord2D() throws IOException {
        return null;
    }

    // REQUIRES: !hasBeenRead
    // MODIFIES: this
    // EFFECTS: reads a Layout from file and returns it
    //          throws IOException if there was an error in reading the file
    public Layout readLayout() throws IOException {
        return null;
    }

    // REQUIRES: !hasBeenRead
    // MODIFIES: this
    // EFFECTS: reads a StringCorpus from file and returns it
    //          throws IOException if there was an error in reading the file
    public StringCorpus readStringCorpus() throws IOException {
        return null;
    }

}
