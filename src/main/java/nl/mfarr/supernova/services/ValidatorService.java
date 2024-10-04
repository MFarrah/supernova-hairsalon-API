// ValidatorService.java
package nl.mfarr.supernova.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
}