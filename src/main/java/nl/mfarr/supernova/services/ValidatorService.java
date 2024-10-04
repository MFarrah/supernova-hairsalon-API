// ValidatorService.java
package nl.mfarr.supernova.services;

import org.springframework.stereotype.Service;

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
}