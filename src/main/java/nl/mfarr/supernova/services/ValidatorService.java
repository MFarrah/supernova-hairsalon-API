// ValidatorService.java
package nl.mfarr.supernova.services;

import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.*;
import nl.mfarr.supernova.repositories.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class ValidatorService {

    @Autowired
    private RosterRepository rosterRepository;

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
//later voor bookingService
    public void validateTimeSlotStatus(TimeSlotStatus status) {
        if (status != TimeSlotStatus.AVAILABLE) {
            if (status != TimeSlotStatus.BOOKED) {
                    throw new TimeSlotBookedException("The requested timeslot is already booked");
                }
            if (status != TimeSlotStatus.UNAVAILABLE) {
                throw new TimeSlotUnavailableException("Employee is unavailable for the requested timeslot");
            }
        }
    }
    public void validateEmployeeExists(EmployeeEntity employee) {
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee does not exist");
        }
    }

    public void validateWorkingSchedule(Set<ScheduleEntity> workingSchedule) {
        if (workingSchedule == null || workingSchedule.isEmpty()) {
            throw new WorkingScheduleNotFoundException("Employee does not have a working schedule");
        }
    }

    public void validateRosterNotExists(EmployeeEntity employee, int month, int year) {
        List<RosterEntity> existingRosters = rosterRepository.findByEmployeeAndMonthAndYear(employee, month, year);
        if (!existingRosters.isEmpty()) {
            throw new RosterAlreadyExistsException("Roster already exists for the given month");
        }
    }

    public boolean hasWorkingScheduleForDay(Set<ScheduleEntity> workingSchedule, LocalDate date) {
        return workingSchedule.stream()
                .anyMatch(schedule -> schedule.getDayOfWeek() == date.getDayOfWeek());
    }


}