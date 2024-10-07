package nl.mfarr.supernova.exceptions;

public class DescriptionRequiredException extends RuntimeException {
    public DescriptionRequiredException(String message) {
        super(message);
    }
}
