package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.GenerateEmployeeMonthRosterRequestDto;
import nl.mfarr.supernova.dtos.RosterResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.exceptions.NoRosterFoundException;
import nl.mfarr.supernova.exceptions.RosterAlreadyExistsException;
import nl.mfarr.supernova.exceptions.WorkingScheduleNotFoundException;
import nl.mfarr.supernova.mappers.RosterMapper;
import nl.mfarr.supernova.repositories.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RosterService {

    private final RosterRepository rosterRepository;
    private final EmployeeService employeeService;
    private final RosterMapper rosterMapper;
    private final ValidatorService validatorService;

    @Autowired
    public RosterService(RosterRepository rosterRepository, EmployeeService employeeService, RosterMapper rosterMapper, ValidatorService validatorService) {
        this.rosterRepository = rosterRepository;
        this.employeeService = employeeService;
        this.rosterMapper = rosterMapper;
        this.validatorService = validatorService;
    }

    public RosterResponseDto getEmployeeMonthlyRoster(Long employeeId, int month, int year) {
        EmployeeEntity employee = employeeService.findById(employeeId);
        List<RosterEntity> rosters = rosterRepository.findByEmployeeAndMonthAndYear(employee, month, year);
        if (rosters.isEmpty()) {
            throw new NoRosterFoundException("No roster found for the given employee's month & year");
        }
        return rosterMapper.toDto(rosters.get(0));
    }

    public RosterEntity generateMonthlyRoster(GenerateEmployeeMonthRosterRequestDto requestDto) {
        Long employeeId = requestDto.getEmployeeId();
        int month = requestDto.getMonth();
        int year = requestDto.getYear();

        // Validate input parameters
        validatorService.validateEmployeeId(employeeId);
        validatorService.validateMonth(month);
        validatorService.validateYear(year);

        // Check if employee exists
        EmployeeEntity employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee does not exist");
        }

        // Check if employee has a working schedule
        Set<ScheduleEntity> workingSchedule = employee.getWorkingSchedule();
        if (workingSchedule == null || workingSchedule.isEmpty()) {
            throw new WorkingScheduleNotFoundException("Employee does not have a working schedule");
        }

        // Check if a roster already exists for the given month and year
        List<RosterEntity> existingRosters = rosterRepository.findByEmployeeAndMonthAndYear(employee, month, year);
        if (!existingRosters.isEmpty()) {
            throw new RosterAlreadyExistsException("Roster already exists for the given month");
        }

        // Generate time slots
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        if (month == today.getMonthValue() && year == today.getYear()) {
            endDate = today;
        }
        List<RosterEntity.TimeSlot> timeSlots = generateTimeSlotsFromSchedule(startDate, endDate, workingSchedule);

        // Initialize and save the roster
        RosterEntity roster = initializeRoster(employee, startDate);
        roster.setTimeSlots(timeSlots);
        return rosterRepository.save(roster);
    }

    private RosterEntity initializeRoster(EmployeeEntity employee, LocalDate startDate) {
        RosterEntity roster = new RosterEntity();
        roster.setEmployee(employee);
        roster.setWeek(startDate.get(WeekFields.ISO.weekOfWeekBasedYear()));
        roster.setMonth(startDate.getMonthValue());
        roster.setYear(startDate.getYear());
        return roster;
    }

    private List<RosterEntity.TimeSlot> generateTimeSlotsFromSchedule(LocalDate startDate, LocalDate endDate, Set<ScheduleEntity> workingSchedule) {
        List<RosterEntity.TimeSlot> timeSlots = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Generate unavailable time slots for the entire day
            timeSlots.addAll(generateUnavailableTimeSlots(date));

            // Update time slots based on the employee's working schedule
            for (ScheduleEntity schedule : workingSchedule) {
                LocalTime startTime = schedule.getStartTime();
                LocalTime endTime = schedule.getEndTime();
                while (startTime.isBefore(endTime)) {
                    timeSlots.add(createTimeSlot(date, startTime, startTime.plusMinutes(15), TimeSlotStatus.AVAILABLE));
                    startTime = startTime.plusMinutes(15);
                }
            }
        }
        return timeSlots;
    }

    private List<RosterEntity.TimeSlot> generateUnavailableTimeSlots(LocalDate date) {
        List<RosterEntity.TimeSlot> timeSlots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(0, 0);
        while (startTime.isBefore(LocalTime.of(23, 45))) {
            timeSlots.add(createTimeSlot(date, startTime, startTime.plusMinutes(15), TimeSlotStatus.UNAVAILABLE));
            startTime = startTime.plusMinutes(15);
        }
        // Add the last slot from 23:45 to 00:00
        timeSlots.add(createTimeSlot(date, LocalTime.of(23, 45), LocalTime.of(0, 0), TimeSlotStatus.UNAVAILABLE));
        return timeSlots;
    }

    private RosterEntity.TimeSlot createTimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime, TimeSlotStatus status) {
        RosterEntity.TimeSlot timeSlot = new RosterEntity.TimeSlot();
        timeSlot.setDate(date);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
        timeSlot.setStatus(status);
        return timeSlot;
    }
}