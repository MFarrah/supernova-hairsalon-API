package nl.mfarr.supernova.exceptions;

public class RosterExistsException extends RuntimeException {
    public RosterExistsException(String message) {
        super(message);
    }
}
