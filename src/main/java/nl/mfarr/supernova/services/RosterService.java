package nl.mfarr.supernova.services;

import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.exceptions.RosterAlreadyGeneratedException;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class RosterService {

    @Autowired
    private RosterRepository rosterRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

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

        // Aanname: werknemer heeft één ScheduleEntity per dag
        ScheduleEntity schedule = employee.getWorkingSchedule().iterator().next();
        Set<LocalTime> timeSlots = generate15MinuteTimeSlots(schedule);

        // Sla tijdsloten op in TimeSlotRepository
        for (LocalTime timeSlot : timeSlots) {
            TimeSlotEntity timeSlotEntity = new TimeSlotEntity();
            timeSlotEntity.setEmployeeId(employee.getEmployeeId());
            timeSlotEntity.setDate(date);
            timeSlotEntity.setStartTime(timeSlot);
            timeSlotEntity.setEndTime(timeSlot.plusMinutes(15));
            timeSlotRepository.save(timeSlotEntity);  // Sla elk tijdslot op
        }

        roster.setTimeSlots(timeSlots);
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

    public void generateAndSaveRosterForEmployee(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        saveMonthlyRoster(employeeId);
    }
}
