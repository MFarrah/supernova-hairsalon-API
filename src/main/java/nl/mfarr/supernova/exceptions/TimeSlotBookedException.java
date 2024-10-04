package nl.mfarr.supernova.exceptions;

public class TimeSlotBookedException extends RuntimeException {
    public TimeSlotBookedException(String message) {
        super(message);
    }
}
