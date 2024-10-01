package nl.mfarr.supernova.exceptions;

public class HasNoWorkingScheduleException extends RuntimeException {
    public HasNoWorkingScheduleException(String message) {
        super(message);
    }
}
