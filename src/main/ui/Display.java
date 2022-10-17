package ui;

import model.KeyboardGeometry;

public class Display<T> {

    private String name;
    private T associatedObject;

    public Display(String name, T object) {
        this.name = name;
        this.associatedObject = object;
    }

    public String getName() {
        return name;
    }

    public T getAssociatedObject() {
        return associatedObject;
    }

}
