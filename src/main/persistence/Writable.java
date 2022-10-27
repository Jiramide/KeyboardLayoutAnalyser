package persistence;

import org.json.JSONObject;

/*
 * An interface that represents classes that can be serialized into JSON
 * Code from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/Writable.java
 */
public interface Writable {

    // EFFECTS: serializes this object into JSON and returns it as a JSONObject
    JSONObject toJson();
}
