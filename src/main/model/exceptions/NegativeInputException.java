package model.exceptions;

public class NegativeInputException extends IllegalArgumentException {

    // MODIFIES: this
    // EFFECTS: creates new NegativeInputException with given string message
    public NegativeInputException(String msg) {
        super(msg);
    }

    // MODIFIES: this
    // EFFECTS: creates new NegativeInputException with default
    //          IllegalArgumentException
    public NegativeInputException() {
        super();
    }
}
