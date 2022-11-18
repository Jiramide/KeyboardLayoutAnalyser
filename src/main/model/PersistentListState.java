package model;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * A class that holds a list of objects and exposes some useful utilities for querying
 * these objects
 */
public class PersistentListState<E extends INameable> extends ArrayList<E> {

    // EFFECTS: gets the object with the given name if found, otherwise return null
    public E get(String name) {
        for (E obj : this) {
            if (obj.getName().equals(name)) {
                return obj;
            }
        }

        return null;
    }

    // EFFECTS: removes and returns the object with the given name if found, otherwise return null and remove nothing
    public E remove(String name) {
        Iterator<E> iterator = iterator();

        while (iterator.hasNext()) {
            E obj = iterator.next();

            if (obj.getName().equals(name)) {
                iterator.remove();

                return obj;
            }
        }

        return null;
    }
}
