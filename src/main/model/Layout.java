package model;

/*
 * A class representing a permutation of keys (aka a keyboard layout).
 */
public class Layout extends Nameable {

    private String layout;

    // EFFECTS: creates a layout with the given name, description; determines the actual permutation through
    //          the given 'layout' string
    public Layout(String name, String description, String layout) {
        super(name, description);
    }

    // EFFECTS: finds the index of the given key in the layout. if the key is not found, return -1.
    public int getIndexOfKey(char key) {
        return layout.indexOf(key);
    }

    // EFFECTS: returns the layout string
    public String getLayoutString() {
        return layout;
    }

}
