// ValidatorService.java
package nl.mfarr.supernova.services;

import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.TimeSlotBookedException;
import nl.mfarr.supernova.exceptions.TimeSlotUnavailableException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static nl.mfarr.supernova.enums.BookingStatus.PENDING;

@Service
public class ValidatorService {

    public void validateEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
    }

    public void validateMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month");
        }
    }

    public void validateYear(int year) {
        if (year < LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Invalid year");
        }
    }

    public void validateTimeSlotStatus(TimeSlotStatus status) {
        if (status != TimeSlotStatus.AVAILABLE) {
            if (status != TimeSlotStatus.BOOKED) {
                    throw new TimeSlotBookedException("Requested timeslot(s) is already booked");
                }
            if (status != TimeSlotStatus.UNAVAILABLE) {
                throw new TimeSlotUnavailableException("Requested timeslot(s) is unavailable");
            }
        }
    }
}