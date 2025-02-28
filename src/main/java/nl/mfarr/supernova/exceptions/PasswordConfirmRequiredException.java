package nl.mfarr.supernova.exceptions;

public class PasswordConfirmRequiredException extends RuntimeException {
    public PasswordConfirmRequiredException(String message) {
        super(message);
    }
}
