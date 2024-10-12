package nl.mfarr.supernova.exceptions;

public class EmployeeExistsByEmailException extends RuntimeException {
    public EmployeeExistsByEmailException(String message) {
        super(message);
    }
}
