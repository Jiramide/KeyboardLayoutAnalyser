package model;

/*
 * An interface version of the Nameable class; allows interfaces to assume names.
 */
public interface INameable {

    // EFFECTS: returns the name of the object
    String getName();

    // EFFECTS: returns the description of the object
    String getDescription();

}
