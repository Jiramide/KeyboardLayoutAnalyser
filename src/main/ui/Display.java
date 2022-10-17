package ui;

import model.KeyboardGeometry;

public class Display<T> {

    private String name;
    private String description;
    private T associatedObject;

    public Display(String name, String description, T object) {
        this.name = name;
        this.description = description;
        this.associatedObject = object;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public T getAssociatedObject() {
        return associatedObject;
    }

}
