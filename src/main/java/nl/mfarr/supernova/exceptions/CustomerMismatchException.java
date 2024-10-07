package nl.mfarr.supernova.exceptions;

public class CustomerMismatchException extends RuntimeException {
    public CustomerMismatchException(String message) {
        super(message);
    }
}
