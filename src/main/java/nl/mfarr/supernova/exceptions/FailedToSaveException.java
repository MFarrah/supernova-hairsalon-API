package nl.mfarr.supernova.exceptions;

public class FailedToSaveException extends RuntimeException {
    public FailedToSaveException(String message) {
        super(message);
    }
}
