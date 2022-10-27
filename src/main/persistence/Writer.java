package persistence;

import org.json.JSONObject;

import java.io.*;

/*
 * Represents a class that interfaces with the file system to write objects into (in JSON format).
 * Structure from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
 */
public class Writer {
    private static final int TAB = 2;

    private BufferedWriter writer;
    private String fileLocation;
    private boolean isOpen;

    // EFFECTS: constructs a closed writer set to write at the given file location
    public Writer(String location) {

    }

    // REQUIRES: !isOpen
    // MODIFIES: this
    // EFFECTS: opens the Writer, allowing it to be written into
    public void open() throws FileNotFoundException, IOException {

    }

    // REQUIRES: isOpen
    // MODIFIES: this
    // EFFECTS: writes the given JSONObject into the file, and closes the Writer
    private void writeJson(JSONObject jsonObject) {

    }

    // REQUIRES: isOpen
    // MODIFIES: this
    // EFFECTS: converts the given Writable into it's JSON formatting and writes it into file. The writer gets
    //          closed afterwards
    public void write(Writable writable) {

    }

}
