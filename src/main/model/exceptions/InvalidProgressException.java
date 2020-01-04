package model.exceptions;

public class InvalidProgressException extends IllegalArgumentException {

    // MODIFIES: this
    // EFFECTS: creates new InvalidProgressException with given string message
    public InvalidProgressException(String msg) {
        super(msg);
    }

    // MODIFIES: this
    // EFFECTS: creates new InvalidProgressException with default
    //          IllegalArgumentException
    public InvalidProgressException() {
        super();
    }
}
