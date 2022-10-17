package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class InputOutput<T> {

    private Scanner input;
    private List<Display<T>> objects;

    public InputOutput(Scanner input) {
        this.input = input;
        this.objects = new ArrayList<>();
    }

    protected void add(Display<T> object) {
        objects.add(object);
    }

    protected abstract void write(int index, Display<T> objectToWrite);

    public void writeAll() {
        int index = 1;

        for (Display<T> display : objects) {
            write(index, display);
            index += 1;
        }
    }

    public abstract void read();

    public T getByName(String name) {
        for (Display<T> display : objects) {
            if (display.getName().equals(name)) {
                return display.getAssociatedObject();
            }
        }

        return null;
    }

    public void removeByName(String name) {
        for (Display<T> display : objects) {
            if (display.getName().equals(name)) {
                objects.remove(display);
                return;
            }
        }
    }

    public String getNameByObject(T object) {
        for (Display<T> display : objects) {
            if (display.getAssociatedObject().equals(object)) {
                return display.getName();
            }
        }

        return null;
    }

    public T query() {
        String name = input.next();
        T object = getByName(name);

        while (object == null) {
            System.out.println("Invalid name!");

            name = input.next();
            object = getByName(name);
        }

        return object;
    }
}
