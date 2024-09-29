package nl.mfarr.supernova.exceptions;

public class NoMatchingPasswordsException extends RuntimeException {
    public NoMatchingPasswordsException(String message) {
        super(message);
    }
}
