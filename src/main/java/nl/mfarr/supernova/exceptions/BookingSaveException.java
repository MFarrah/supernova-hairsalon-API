package nl.mfarr.supernova.exceptions;

public class BookingSaveException extends RuntimeException {
    public BookingSaveException(String message) {
        super(message);
    }
}
