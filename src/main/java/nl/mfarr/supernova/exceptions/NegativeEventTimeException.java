package nl.mfarr.supernova.exceptions;

public class NegativeEventTimeException extends RuntimeException {
    public NegativeEventTimeException(String message) {
        super(message);
    }
}
