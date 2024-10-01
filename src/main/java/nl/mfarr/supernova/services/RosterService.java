package nl.mfarr.supernova.services;

import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        // Assume the employee has only one ScheduleEntity per day
        ScheduleEntity schedule = employee.getWorkingSchedule().iterator().next();
        roster.setTimeSlots(generate15MinuteTimeSlots(schedule));

        return roster;
    }

    public List<RosterEntity> generateMonthlyRoster(EmployeeEntity employee) {
        List<RosterEntity> monthlyRoster = new ArrayList<>();
        List<LocalDate> daysOfMonth = getDaysOfMonth();

        for (LocalDate date : daysOfMonth) {
            monthlyRoster.add(copyScheduleForDay(employee, date));
        }

        return monthlyRoster;
    }

    public void saveMonthlyRoster(EmployeeEntity employee) {
        List<RosterEntity> monthlyRoster = generateMonthlyRoster(employee);
        rosterRepository.saveAll(monthlyRoster);
    }

    public void generateAndSaveRosterForEmployee(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        saveMonthlyRoster(employee);
    }
}