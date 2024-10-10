package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.dtos.GenerateEmployeeMonthRosterRequestDto;
import nl.mfarr.supernova.dtos.RosterResponseDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.NoRosterFoundException;
import nl.mfarr.supernova.mappers.RosterMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RosterService {

    private final RosterRepository rosterRepository;
    private final EmployeeService employeeService;
    private final RosterMapper rosterMapper;
    private final ValidatorService validatorService;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public RosterService(RosterRepository rosterRepository, EmployeeService employeeService, RosterMapper rosterMapper, ValidatorService validatorService, EmployeeRepository employeeRepository) {
        this.rosterRepository = rosterRepository;
        this.employeeService = employeeService;
        this.rosterMapper = rosterMapper;
        this.validatorService = validatorService;
        this.employeeRepository = employeeRepository;
    }

    public RosterEntity generateMonthlyRoster(GenerateEmployeeMonthRosterRequestDto requestDto) {
        Long employeeId = requestDto.getEmployeeId();
        int month = requestDto.getMonth();
        int year = requestDto.getYear();

        // Validate input parameters
        validatorService.validateEmployeeId(employeeId);
        validatorService.validateMonth(month);
        validatorService.validateYear(year);
        EmployeeResponseDto employee = employeeService.getEmployeeById(employeeId);
        validatorService.validateEmployeeExists(employee);
        Set<ScheduleResponseDto> workingSchedule = employee.getWorkingSchedule();
        validatorService.validateWorkingSchedule(workingSchedule);
        validatorService.validateRosterNotExists(employee, month, year);

        // Generate time slots
        LocalDate today = LocalDate.now();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        if (month == today.getMonthValue() && year == today.getYear()) {
            endDate = today;
        }
        List<TimeSlotEntity> timeSlots = generateTimeSlotsFromSchedule(startDate, endDate, workingSchedule);

        // Initialize and save the roster
        RosterEntity roster = initializeRoster(employee, startDate);
        roster.setTimeSlots(timeSlots);
        return rosterRepository.save(roster);
    }

    private RosterEntity initializeRoster(EmployeeResponseDto employee, LocalDate startDate) {
        RosterEntity roster = new RosterEntity();
        roster.setEmployee(employeeRepository.findById(employee.getEmployeeId()).orElseThrow());
        roster.setMonth(startDate.getMonthValue());
        roster.setYear(startDate.getYear());

        // Initialize the TimeSlot(s) for this Roster
        List<TimeSlotEntity> timeSlots = new ArrayList<>();

        // Assuming you want to create one or more time slots based on the employee's roster
        TimeSlotEntity timeSlot = new TimeSlotEntity();
        timeSlot.setWeek(startDate.get(WeekFields.ISO.weekOfWeekBasedYear())); // Correct way to set the week for the TimeSlot
        timeSlot.setDate(startDate); // Set the date for this time slot (or other relevant details)

        // Add the TimeSlot to the roster
        timeSlots.add(timeSlot);
        roster.setTimeSlots(timeSlots); // Assuming you have a setter for timeSlots in RosterEntity

        return roster;
    }

    private List<TimeSlotEntity> generateTimeSlotsFromSchedule(LocalDate startDate, LocalDate endDate, Set<ScheduleResponseDto> workingSchedule) {
        List<TimeSlotEntity> timeSlots = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Check if the day has any working schedule using ValidatorService
            boolean hasSchedule = validatorService.hasWorkingScheduleForDay(workingSchedule, date);

            // Generate unavailable time slots for the entire day
            timeSlots.addAll(generateUnavailableTimeSlots(date));

            if (hasSchedule) {
                // Update time slots based on the employee's working schedule
                for (ScheduleResponseDto schedule : workingSchedule) {
                    if (schedule.getDayOfWeek() == date.getDayOfWeek()) {
                        LocalTime startTime = schedule.getStartTime();
                        LocalTime endTime = schedule.getEndTime();
                        while (startTime.isBefore(endTime)) {
                            // Remove the corresponding unavailable timeslot
                            LocalDate finalDate = date;
                            LocalTime finalStartTime = startTime;
                            timeSlots.removeIf(slot -> slot.getDate().equals(finalDate) && slot.getStartTime().equals(finalStartTime));
                            // Add available timeslot
                            timeSlots.add(createTimeSlot(date, startTime, startTime.plusMinutes(15), TimeSlotStatus.AVAILABLE));
                            startTime = startTime.plusMinutes(15);
                        }
                    }
                }
            }
        }
        return timeSlots;
    }

    private List<TimeSlotEntity> generateUnavailableTimeSlots(LocalDate date) {
        List<TimeSlotEntity> timeSlots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(0, 0);
        while (startTime.isBefore(LocalTime.of(23, 45))) {
            timeSlots.add(createTimeSlot(date, startTime, startTime.plusMinutes(15), TimeSlotStatus.UNAVAILABLE));
            startTime = startTime.plusMinutes(15);
        }
        // Add the last slot from 23:45 to 00:00
        timeSlots.add(createTimeSlot(date, LocalTime.of(23, 45), LocalTime.of(0, 0), TimeSlotStatus.UNAVAILABLE));
        return timeSlots;
    }

    private TimeSlotEntity createTimeSlot(LocalDate date, LocalTime startTime, LocalTime endTime, TimeSlotStatus status) {
        TimeSlotEntity timeSlot = new TimeSlotEntity();
        timeSlot.setDate(date);
        timeSlot.setStartTime(startTime);
        timeSlot.setEndTime(endTime);
        timeSlot.setStatus(status);
        timeSlot.setWeek(date.get(WeekFields.ISO.weekOfWeekBasedYear())); // Set the week based on the date
        return timeSlot;
    }

 /*   public RosterResponseDto getEmployeeMonthlyRoster(Long employeeId, int month, int year) {
        EmployeeEntity employee = employeeService.findById(employeeId);
        List<RosterEntity> rosters = rosterRepository.findByEmployeeAndMonthAndYear(employee, month, year);
        if (rosters.isEmpty()) {
            throw new NoRosterFoundException("No roster found for the given employee's month & year");
        }
        RosterEntity roster = rosters.get(0);
        List<TimeSlotEntity> filteredTimeSlots = roster.getTimeSlots().stream()
                .filter(timeSlot -> timeSlot.getStatus() == TimeSlotStatus.AVAILABLE || timeSlot.getStatus() == TimeSlotStatus.BOOKED)
                .collect(Collectors.toList());
        roster.setTimeSlots(filteredTimeSlots);
        return rosterMapper.toDto(roster);
    }*/

    /*public RosterResponseDto getEmployeeWeeklyRoster(Long employeeId, int week, int year) {
        EmployeeEntity employee = employeeService.findById(employeeId);
        List<RosterEntity> rosters = rosterRepository.findByEmployeeAndWeekAndYear(employee, week, year);
        if (rosters.isEmpty()) {
            throw new NoRosterFoundException("No roster found for the given employee's week & year");
        }
        RosterEntity roster = rosters.get(0);
        List<TimeSlotEntity> filteredTimeSlots = roster.getTimeSlots().stream()
                .filter(timeSlot -> timeSlot.getWeek() == week &&
                        (timeSlot.getStatus() == TimeSlotStatus.AVAILABLE ||
                                timeSlot.getStatus() == TimeSlotStatus.BOOKED))
                .collect(Collectors.toList());
        roster.setTimeSlots(filteredTimeSlots);
        return rosterMapper.toDto(roster);
    }*/

    public RosterResponseDto getEmployeeDailyRoster(Long employeeId, LocalDate date) {
        EmployeeEntity employee = employeeRepository.findById(employeeId).orElseThrow();
        Collection<Object> rosters = Collections.singleton(rosterRepository.findByEmployeeIdAndMonthAndYear(employee.getId(), date.getMonthValue(), date.getYear()));
        if (rosters.isEmpty()) {
            throw new NoRosterFoundException("No roster found for the given employee's date");
        }
        RosterEntity roster = (RosterEntity) rosters.stream().findFirst().orElseThrow(() -> new NoRosterFoundException("No roster found for the given employee's date"));
        List<TimeSlotEntity> filteredTimeSlots = roster.getTimeSlots().stream()
                .filter(timeSlot -> timeSlot.getDate().equals(date) &&
                        (timeSlot.getStatus() == TimeSlotStatus.AVAILABLE ||
                                timeSlot.getStatus() == TimeSlotStatus.BOOKED))
                .collect(Collectors.toList());
        roster.setTimeSlots(filteredTimeSlots);
        return rosterMapper.toDto(roster);
    }
}