package nl.mfarr.supernova.exceptions;

public class EmailRequiredException extends RuntimeException {

    public EmailRequiredException(String message) {
        super(message);
    }
}
