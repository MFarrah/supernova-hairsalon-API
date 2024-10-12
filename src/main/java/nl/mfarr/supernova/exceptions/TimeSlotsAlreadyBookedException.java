package nl.mfarr.supernova.exceptions;

public class TimeSlotsAlreadyBookedException extends RuntimeException {
    public TimeSlotsAlreadyBookedException(String message) {
        super(message);
    }
}
