package ui;

import org.json.JSONObject;
import persistence.Writable;

/*
 * A simple class that holds display data for objects. Includes a name
 * and description for objects that don't contain those fields (which
 * is useful for UI).
 */
public class Display<T> implements Writable {

    private String name;
    private String description;
    private T associatedObject;

    // EFFECTS: constructs a Display with the given name, description and object
    public Display(String name, String description, T object) {
        this.name = name;
        this.description = description;
        this.associatedObject = object;
    }

    // EFFECTS: returns the name of the display
    public String getName() {
        return name;
    }

    // EFFECTS: returns the description of the display
    public String getDescription() {
        return description;
    }

    // EFFECTS: returns the associated object of the display
    public T getAssociatedObject() {
        return associatedObject;
    }

    // EFFECTS: If the object inside implements Writable, serializes the internal object
    //          and attaches its name and description into the JSONObject, then returns that JSONObject,
    //          otherwise, returns null
    @Override
    public JSONObject toJson() {
        if (associatedObject instanceof Writable) {
            // honestly I don't really like this implementation, so I'll put a TODO here to clean it up:
            // 1) make this class have an upper bound of Writable for the type parameter T
            // 2) or make a separate interface for displayable objects that holds their name and desc
            // Probably go with #2. Seems a lot cleaner, but hopefully this temporary measure works
            // for now.
            Writable writableObject = (Writable) getAssociatedObject();

            JSONObject object = writableObject.toJson();
            object.put("name", getName());
            object.put("description", getDescription());

            return object;
        }

        return null;
    }

}
