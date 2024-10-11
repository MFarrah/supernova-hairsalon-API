package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.rosterDtos.GenerateMonthRosterRequestDto;
import nl.mfarr.supernova.dtos.rosterDtos.RosterResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.exceptions.NoRosterFoundException;
import nl.mfarr.supernova.mappers.TimeSlotMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RosterService {

    @Autowired
    private RosterRepository rosterRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void createRoster(GenerateMonthRosterRequestDto request) {
        EmployeeEntity employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        // Check if roster already exists
        if (rosterRepository.existsByEmployeeAndYearAndMonth(employee, request.getYear(), request.getMonth())) {
            throw new IllegalArgumentException("Roster already exists for this month");
        }
        RosterEntity roster = new RosterEntity();
        roster.setEmployee(employee);
        roster.setYear(request.getYear());
        roster.setMonth(request.getMonth());
        roster = rosterRepository.save(roster);

        // Get the number of days in the month
        int daysInMonth = LocalDate.of(request.getYear(), request.getMonth(), 1).lengthOfMonth();

        // Generate time slots for each day in the month
        List<TimeSlotEntity> timeSlots = new ArrayList<>();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(request.getYear(), request.getMonth(), day);
            for (ScheduleEntity schedule : employee.getWorkingSchedule()) {
                LocalTime startTime = schedule.getStartTime();
                LocalTime endTime = schedule.getEndTime();
                while (startTime.isBefore(endTime)) {
                    TimeSlotEntity timeSlot = new TimeSlotEntity();
                    timeSlot.setEmployee(employee);
                    timeSlot.setRoster(roster);
                    int weekOfYear = date.get(WeekFields.ISO.weekOfYear());
                    timeSlot.setWeek(weekOfYear);
                    timeSlot.setDate(date);
                    timeSlot.setStartTime(startTime);
                    timeSlot.setEndTime(startTime.plusMinutes(15));
                    timeSlot.setStatus(TimeSlotStatus.AVAILABLE);
                    timeSlots.add(timeSlot);
                    startTime = startTime.plusMinutes(15);
                }
            }
        }
        timeSlotRepository.saveAll(timeSlots);
        roster.setTimeSlots(timeSlots);
        rosterRepository.save(roster);
    }
}


