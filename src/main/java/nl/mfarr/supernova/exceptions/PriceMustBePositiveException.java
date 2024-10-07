package nl.mfarr.supernova.exceptions;

public class PriceMustBePositiveException extends RuntimeException {
    public PriceMustBePositiveException(String message) {
        super(message);
    }
}
