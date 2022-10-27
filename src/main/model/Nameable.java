package model;

import org.json.JSONObject;

/*
 * Represents a class that can be named; useful for user interaction.
 */
public abstract class Nameable {

    private String name;
    private String description;

    // EFFECTS: creates a Nameable with the given name and description
    public Nameable(String name, String description) {

    }

    // EFFECTS: creates a Nameable with the given name and a blank ("") description
    public Nameable(String name) {

    }

    // EFFECTS: returns the name of the object
    public String getName() {
        return null;
    }

    // EFFECTS: returns the description of the object
    public String getDescription() {
        return null;
    }

    // EFFECTS: returns a JSONObject with keys "name", "description" mapping to name and description respectively.
    public JSONObject getBaseJson() {
        return null;
    }

}
