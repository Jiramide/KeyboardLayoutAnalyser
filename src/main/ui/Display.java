package ui;

/*
 * A simple class that holds display data for objects. Includes a name
 * and description for objects that don't contain those fields (which
 * is useful for UI).
 */
public class Display<T> {

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

}
