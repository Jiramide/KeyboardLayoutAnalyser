package persistence;

import model.Coord2D;
import model.KeyboardGeometry;
import model.Layout;
import model.corpora.StringCorpus;
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
    private String readFile() {
        return "";
    }

    // EFFECTS: reads a KeyboardGeometry from the given JSONObject
    public KeyboardGeometry readKeyboardGeometry(JSONObject jsonObject) {
        return null;
    }

    // EFFECTS: reads a Layout from the given JSONObject
    public Layout readLayout(JSONObject jsonObject) {
        return null;
    }

    // EFFECTS: reads a Coord2D from the given JSONObject
    public Coord2D readCoord2D(JSONObject jsonObject) {
        return null;
    }

    // EFFECTS: reads a StringCorpus from the given JSONObject
    public StringCorpus readStringCorpus(JSONObject jsonObject) {
        return null;
    }

}
