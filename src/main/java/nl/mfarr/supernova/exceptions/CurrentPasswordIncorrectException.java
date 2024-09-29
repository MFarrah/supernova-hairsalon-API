package nl.mfarr.supernova.exceptions;

public class CurrentPasswordIncorrectException extends RuntimeException {

    public CurrentPasswordIncorrectException(String message) {
        super(message);
    }
}
