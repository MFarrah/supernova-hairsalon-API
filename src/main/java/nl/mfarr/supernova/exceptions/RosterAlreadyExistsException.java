package nl.mfarr.supernova.exceptions;

public class RosterAlreadyExistsException extends RuntimeException {
    public RosterAlreadyExistsException(String message) {
        super(message);
    }
}
