package nl.mfarr.supernova.exceptions;

public class PriceRequiredException extends RuntimeException {
    public PriceRequiredException(String message) {
        super(message);
    }
}
