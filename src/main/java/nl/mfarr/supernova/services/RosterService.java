package nl.mfarr.supernova.services;

import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.*;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class RosterService {

    @Autowired
    private RosterRepository rosterRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<LocalDate> getDaysOfMonth() {
        List<LocalDate> days = new ArrayList<>();
        LocalDate now = LocalDate.now();
        int daysInMonth = now.lengthOfMonth();

        for (int i = 1; i <= daysInMonth; i++) {
            days.add(now.withDayOfMonth(i));
        }

        return days;
    }

    public Set<LocalTime> generate15MinuteTimeSlots(ScheduleEntity schedule) {
        Set<LocalTime> timeSlots = new HashSet<>();
        LocalTime startTime = schedule.getStartTime();
        LocalTime endTime = schedule.getEndTime();
        TimeSlotStatus status = TimeSlotStatus.AVAILABLE;

        while (startTime.isBefore(endTime)) {
            timeSlots.add(startTime);
            startTime = startTime.plusMinutes(15);
        }

        return timeSlots;
    }

    public RosterEntity copyScheduleForDay(EmployeeEntity employee, LocalDate date) {
        RosterEntity roster = new RosterEntity();
        roster.setEmployee(employee);
        roster.setDate(date);
        roster.setMonth(date.getMonthValue());
        roster.setYear(date.getYear());

        // Assume the employee has only one ScheduleEntity per day
        ScheduleEntity schedule = employee.getWorkingSchedule().iterator().next();
        roster.setTimeSlots(generate15MinuteTimeSlots(schedule));

        return roster;
    }

    public List<RosterEntity> generateMonthlyRoster(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        LocalDate now = LocalDate.now();
        LocalDate firstOfMonth = now.withDayOfMonth(1);

        Optional<RosterEntity> existingRoster = rosterRepository
                .findByEmployeeAndMonthAndYear(employee, firstOfMonth.getMonthValue(), firstOfMonth.getYear());

        if (existingRoster.isPresent()) {
            throw new RosterAlreadyGeneratedException("Roster already generated for this employee for the current month");
        }

        List<RosterEntity> monthlyRoster = new ArrayList<>();
        for (LocalDate date : getDaysOfMonth()) {
            monthlyRoster.add(copyScheduleForDay(employee, date));
        }
        return monthlyRoster;
    }

    public void saveMonthlyRoster(Long employeeId) {
        List<RosterEntity> monthlyRoster = generateMonthlyRoster(employeeId);
        rosterRepository.saveAll(monthlyRoster);
    }

    public void generateAndSaveRosterForEmployee(Long id) {
        // Get employee
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        if (employee.getWorkingSchedule().isEmpty()) {
            throw new HasNoWorkingScheduleException("Employee has no working schedule");
        }
        if (rosterRepository.existsByEmployeeAndDate(employee, LocalDate.now())) {
            throw new RosterExistsException("Roster already exists for employee");
        }

        // Generate and save roster
        List<RosterEntity> monthlyRoster = generateMonthlyRoster(employee.getId());
        rosterRepository.saveAll(monthlyRoster);
    }

    public String getRosterForEmployee(Long id) {
        // Get employee
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        // Get roster
        List<RosterEntity> roster = rosterRepository.findByEmployee(employee);
        if (roster.isEmpty()) {
            throw new NoRosterFoundException("No roster found for employee");
        }
        return roster.stream()
                .map(RosterEntity::toString)
                .collect(Collectors.joining(", "));
    }
}


