package nl.mfarr.supernova.exceptions;

public class NoRosterFoundException extends RuntimeException {
    public NoRosterFoundException(String message) {
        message=("Roster already generated for employee");
    }
}
