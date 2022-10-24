package persistence;

import org.json.JSONObject;

/*
 * An interface that represents classes that can be serialized into JSON
 */
public interface Writable {

    // EFFECTS: serializes this object into JSON and returns it as a JSONObject
    JSONObject toJson();
}
