package nl.mfarr.supernova.exceptions;

public class TimeSlotNotFoundException extends RuntimeException {
    public TimeSlotNotFoundException(String message) {
        super(message);
    }
}
