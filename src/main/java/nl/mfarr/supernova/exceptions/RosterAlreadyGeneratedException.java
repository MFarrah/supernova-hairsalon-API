package nl.mfarr.supernova.exceptions;

public class  RosterAlreadyGeneratedException extends RuntimeException {
    public RosterAlreadyGeneratedException(String message) {
        super(message);
    }
}
