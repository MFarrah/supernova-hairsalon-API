package nl.mfarr.supernova.exceptions;

public class WorkingScheduleNotFoundException extends RuntimeException {
    public WorkingScheduleNotFoundException(String message) {
        super(message);
    }
}
