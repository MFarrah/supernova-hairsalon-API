package nl.mfarr.supernova.exceptions;

public class DurationPositiveException extends RuntimeException {
    public DurationPositiveException(String message) {
        super(message);
    }
}
