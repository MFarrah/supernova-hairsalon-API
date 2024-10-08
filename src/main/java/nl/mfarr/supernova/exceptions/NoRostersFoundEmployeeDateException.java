package nl.mfarr.supernova.exceptions;

public class NoRostersFoundEmployeeDateException extends RuntimeException {
    public NoRostersFoundEmployeeDateException(String message) {
        super(message);
    }
}
