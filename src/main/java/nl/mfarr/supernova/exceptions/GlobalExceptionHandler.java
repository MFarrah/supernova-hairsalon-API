package nl.mfarr.supernova.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInput(InvalidInputException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<String> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailRequiredException.class)
    public ResponseEntity<String> handleEmailRequiredException(EmailRequiredException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //throw new RuntimeException("Current password is incorrect");
    @ExceptionHandler(CurrentPasswordIncorrectException.class)
    public ResponseEntity<String> handleCurrentPasswordIncorrect(CurrentPasswordIncorrectException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoMatchingPasswordsException.class)
    public ResponseEntity<String> handleNoMatchingPasswords(NoMatchingPasswordsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotReconizedException.class)
    public ResponseEntity<String> handleRoleNotReconized(RoleNotReconizedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoRosterFoundException.class)
    public ResponseEntity<String> handleNoRosterFound(NoRosterFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFound(EmployeeNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HasNoWorkingScheduleException.class)
    public ResponseEntity<String> handleHasNoWorkingSchedule(HasNoWorkingScheduleException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RosterExistsException.class)
    public ResponseEntity<String> handleRosterExists(RosterExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RosterAlreadyGeneratedException.class)
    public ResponseEntity<String> handleRosterAlreadyGenerated(RosterAlreadyGeneratedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WorkingScheduleNotFoundException.class)
    public ResponseEntity<String> handleWorkingScheduleNotFound(WorkingScheduleNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RosterAlreadyExistsException.class)
    public ResponseEntity<String> handleRosterAlreadyExists(RosterAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeSlotBookedException.class)
    public ResponseEntity<String> handleTimeSlotBooked(TimeSlotBookedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeSlotUnavailableException.class)
    public ResponseEntity<String> handleTimeSlotUnavailable(TimeSlotUnavailableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmail(DuplicateEmailException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DurationPositiveException.class)
    public ResponseEntity<String> handleDurationPositiveException(DurationPositiveException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DescriptionRequiredException.class)
    public ResponseEntity<String> handleDescriptionRequiredException(DescriptionRequiredException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DescriptionUniqueException.class)
    public ResponseEntity<String> handleDescriptionUniqueException(DescriptionUniqueException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PriceRequiredException.class)
    public ResponseEntity<String> handlePriceRequiredException(PriceRequiredException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PriceMustBePositiveException.class)
    public ResponseEntity<String> handlePriceMustBePositiveException(PriceMustBePositiveException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerMismatchException.class)
    public ResponseEntity<String> handleCustomerMismatchException(CustomerMismatchException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBookingRequestException.class)
    public ResponseEntity<String> handleInvalidBookingRequestException(InvalidBookingRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FailedToSaveException.class)
    public ResponseEntity<String> handleFailedToSaveException(FailedToSaveException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BookingSaveException.class)
    public ResponseEntity<String> handleBookingSaveException(BookingSaveException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoRostersFoundEmployeeDateException.class)
    public ResponseEntity<String> handleNoRostersFoundEmployeeDateException(NoRostersFoundEmployeeDateException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RosterNotFoundException.class)
    public ResponseEntity<String> handleRosterNotFoundException(RosterNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientTimeSlotsException.class)
    public ResponseEntity<String> handleInsufficientTimeSlotsException(InsufficientTimeSlotsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmployeeNotQualifiedException.class)
    public ResponseEntity<String> handleEmployeeNotQualifiedException(EmployeeNotQualifiedException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
