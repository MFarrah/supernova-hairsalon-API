package nl.mfarr.supernova.exceptions;

public class RosterNotFoundException extends RuntimeException {
    public RosterNotFoundException(String message) {
        super(message);
    }
}
