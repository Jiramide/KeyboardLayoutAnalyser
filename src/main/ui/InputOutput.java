package ui;

import model.Nameable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * A class that keeps track of a list of objects and allows reading and writing
 * these objects into the console. This is the base class that's inherited by the
 * other *IO classes and acts as a convenient way to not retype writeAll, query, etc.
 */
public abstract class InputOutput<T extends Nameable> {

    private Scanner input;
    private List<T> objects;

    // EFFECTS: constructs an InputOutput object with the given scanner as the input method
    public InputOutput(Scanner input) {
        this.input = input;
        this.objects = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the object into the list of tracked objects
    public void add(T object) {
        objects.add(object);
    }

    protected abstract void write(int index, T objectToWrite);

    // EFFECTS: writes all of the tracked objects into the console.
    public void writeAll() {
        int index = 1;

        for (T display : objects) {
            write(index, display);
            index += 1;
        }
    }

    public abstract void read();

    // REQUIRES: name is in InputOutput
    // EFFECTS: finds the object with the given name and returns it
    public T getByName(String name) {
        for (T object : objects) {
            if (object.getName().equals(name)) {
                return object;
            }
        }

        return null;
    }

    // REQUIRES: name is in InputOutput
    // MODIFIES: this
    // EFFECTS: finds the object with the given name and removes it
    public void removeByName(String name) {
        for (T object : objects) {
            if (object.getName().equals(name)) {
                objects.remove(object);
                return;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: interacts with the user to remove an object
    public void remove(String message) {
        System.out.print(message);

        String name = input.next();
        removeByName(name);
    }

    // EFFECTS: returns the name associated with an object
    public String getNameByObject(T objectToFind) {
        for (T object : objects) {
            if (object.equals(objectToFind)) {
                return object.getName();
            }
        }

        return null;
    }

    // EFFECTS: interacts with the user to retrieve an object with the provided name
    public T query(String message) {
        System.out.print(message);

        String name = input.next();
        T object = getByName(name);

        while (object == null) {
            System.out.print("Invalid name! " + message);

            name = input.next();
            object = getByName(name);
        }

        return object;
    }
}
