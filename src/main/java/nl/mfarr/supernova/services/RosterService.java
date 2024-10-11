package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.*;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.exceptions.NoRosterFoundException;
import nl.mfarr.supernova.mappers.RosterMapper;
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

    public void createRoster(Long employeeId, int year, int month) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        // Check if roster already exists
        if (rosterRepository.existsByEmployeeAndYearAndMonth(employee, year, month)) {
            throw new IllegalArgumentException("Roster already exists for this month");
        }
        RosterEntity roster = new RosterEntity();
        roster.setEmployee(employee);
        roster.setYear(year);
        roster.setMonth(month);
        roster = rosterRepository.save(roster);
        // Get the number of days in the month
        int daysInMonth = LocalDate.of(year, month, 1).lengthOfMonth();

        // Generate time slots for each day in the month
        List<TimeSlotEntity> timeSlots = new ArrayList<>();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);
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

        // Create a new roster
        roster.setTimeSlots(timeSlots);
        rosterRepository.save(roster);
    }

    //GET api/monthly/{employeeId}/{month}/{year} - Get the roster for the given employee and month
    public RosterResponseDto getRoster(Long employeeId, int month, int year) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        RosterEntity roster = rosterRepository.findByEmployeeIdAndMonthAndYear(employeeId, month, year)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoRosterFoundException("Roster not found"));

        RosterResponseDto responseDto = new RosterResponseDto();
        responseDto.setEmployeeId(employeeId);
        responseDto.setMonth(month);
        responseDto.setYear(year);
        responseDto.setTimeSlots(roster.getTimeSlots().stream()
                .map(TimeSlotMapper::toDto)
                .collect(Collectors.toList()));

        return responseDto;
    }

    // /weekly/{employeeId}/{week}/{year} - Get the roster for the given employee and week
    public RosterResponseDto getRosterByWeek(Long employeeId, int week, int year) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        List<RosterEntity> rosters = rosterRepository.findByEmployeeIdAndWeekAndYear(employeeId, week, year);
        if (rosters.isEmpty()) {
            throw new NoRosterFoundException("Roster not found");
        }

        RosterResponseDto responseDto = new RosterResponseDto();
        responseDto.setEmployeeId(employeeId);
        responseDto.setWeek(week);
        responseDto.setYear(year);
        responseDto.setTimeSlots(rosters.stream()
                .flatMap(roster -> roster.getTimeSlots().stream())
                .map(TimeSlotMapper::toDto)
                .collect(Collectors.toList()));

        return responseDto;
    }

    //daily/{employeeId}/{date} - Get the roster for the given employee and date
    public RosterResponseDto getRosterByDate(Long employeeId, LocalDate date) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        RosterEntity roster = rosterRepository.findByEmployeeIdAndDate(employeeId, date)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoRosterFoundException("Roster not found"));

        RosterResponseDto responseDto = new RosterResponseDto();
        responseDto.setEmployeeId(employeeId);
        responseDto.setDate(date);
        responseDto.setTimeSlots(roster.getTimeSlots().stream()
                .map(TimeSlotMapper::toDto)
                .collect(Collectors.toList()));

        return responseDto;
    }

}
