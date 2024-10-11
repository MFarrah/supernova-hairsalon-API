package nl.mfarr.supernova.exceptions;

public class InsufficientTimeSlotsException extends RuntimeException {
    public InsufficientTimeSlotsException(String message) {
        super(message);
    }
}
