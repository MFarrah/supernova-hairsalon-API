package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.rosterDtos.CustomRosterResponseDto;
import nl.mfarr.supernova.dtos.rosterDtos.GenerateMonthRosterRequestDto;
import nl.mfarr.supernova.dtos.rosterDtos.RosterMonthRequestDto;
import nl.mfarr.supernova.dtos.rosterDtos.RosterResponseDto;
import nl.mfarr.supernova.dtos.timeSlotDtos.CustomRosterRequestDto;
import nl.mfarr.supernova.dtos.timeSlotDtos.EmployeeMonthRequestDto;
import nl.mfarr.supernova.dtos.timeSlotDtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.CreateRosterFailedException;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.exceptions.TimeSlotBookedException;
import nl.mfarr.supernova.mappers.RosterMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RosterService {

    @Autowired
    private RosterRepository rosterRepository;

    @Autowired
    private RosterMapper rosterMapper;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public void createMonthRoster(GenerateMonthRosterRequestDto request) {
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

    public CustomRosterResponseDto createCustomRoster(CustomRosterRequestDto customRosterRequestDto) {
        EmployeeEntity employee = employeeRepository.findById(customRosterRequestDto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        if (customRosterRequestDto.getEventStartTime().isAfter(customRosterRequestDto.getEventEndTime())) {
            throw new IllegalArgumentException("Event start time cannot be after event end time");
        }

        if (customRosterRequestDto.getStatus() != TimeSlotStatus.AVAILABLE) {
            throw new IllegalArgumentException("Invalid status");
        }

        List<RosterEntity> rosterList = rosterRepository.findByEmployeeAndYearAndMonth(employee,
                customRosterRequestDto.getDate().getYear(),
                customRosterRequestDto.getDate().getMonthValue());

        RosterEntity roster;
        if (rosterList.isEmpty()) {
            // Generate a month roster if it does not exist
            GenerateMonthRosterRequestDto monthRosterRequest = new GenerateMonthRosterRequestDto();
            monthRosterRequest.setEmployeeId(customRosterRequestDto.getEmployeeId());
            monthRosterRequest.setYear(customRosterRequestDto.getDate().getYear());
            monthRosterRequest.setMonth(customRosterRequestDto.getDate().getMonthValue());
            createMonthRoster(monthRosterRequest);

            // Retrieve the newly created roster
            rosterList = rosterRepository.findByEmployeeAndYearAndMonth(employee,
                    customRosterRequestDto.getDate().getYear(),
                    customRosterRequestDto.getDate().getMonthValue());

            if (rosterList.isEmpty()) {
                throw new CreateRosterFailedException("Failed to create month roster");
            }
        }

        roster = rosterList.get(0);

        List<TimeSlotEntity> existingTimeSlots = timeSlotRepository.findByRosterAndDate(roster, customRosterRequestDto.getDate());

        // Check if any existing time slot has status BOOKED
        boolean hasBookedSlots = existingTimeSlots.stream()
                .anyMatch(timeSlot -> timeSlot.getStatus() == TimeSlotStatus.BOOKED);
        if (hasBookedSlots) {
            throw new TimeSlotBookedException("Cannot overwrite roster with booked time slots");
        }

        timeSlotRepository.deleteAll(existingTimeSlots);

        List<TimeSlotEntity> newTimeSlots = new ArrayList<>();
        LocalTime startTime = customRosterRequestDto.getEventStartTime();
        LocalTime endTime = customRosterRequestDto.getEventEndTime();

        while (startTime.isBefore(endTime)) {
            TimeSlotEntity timeSlot = new TimeSlotEntity();
            timeSlot.setEmployee(employee);
            timeSlot.setRoster(roster);
            timeSlot.setDate(customRosterRequestDto.getDate());
            timeSlot.setStartTime(startTime);
            timeSlot.setEndTime(startTime.plusMinutes(15));
            timeSlot.setStatus(TimeSlotStatus.AVAILABLE);
            newTimeSlots.add(timeSlot);
            startTime = startTime.plusMinutes(15);
        }

        timeSlotRepository.saveAll(newTimeSlots);
        roster.setTimeSlots(newTimeSlots);
        rosterRepository.save(roster);

        CustomRosterResponseDto responseDto = new CustomRosterResponseDto();
        responseDto.setRosterId(roster.getId());
        responseDto.setEmployeeId(employee.getId());
        responseDto.setTimeSlots(newTimeSlots.stream()
                .map(timeSlot -> new TimeSlotResponseDto(timeSlot.getId(), timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime(), timeSlot.getStatus()))
                .collect(Collectors.toList()));

        return responseDto;
    }

    public RosterResponseDto getRosterForMonth(RosterMonthRequestDto requestDto) {
        List<RosterEntity> rosterEntities = rosterRepository.findByEmployeeIdAndMonthAndYear(
                requestDto.getEmployeeId(), requestDto.getMonth(), requestDto.getYear());

        if (rosterEntities.isEmpty()) {
            return null; // or throw an exception if preferred
        }

        RosterEntity rosterEntity = rosterEntities.get(0);
        List<TimeSlotResponseDto> timeSlotResponseDtos = rosterEntity.getTimeSlots().stream()
                .map(timeSlot -> new TimeSlotResponseDto(timeSlot.getId(), timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime(), timeSlot.getStatus()))
                .collect(Collectors.toList());

        RosterResponseDto rosterResponseDto = new RosterResponseDto();
        rosterResponseDto.setId(rosterEntity.getId());
        rosterResponseDto.setEmployeeId(rosterEntity.getEmployee().getId());
        rosterResponseDto.setMonth(rosterEntity.getMonth());
        rosterResponseDto.setYear(rosterEntity.getYear());
        rosterResponseDto.setTimeSlots(timeSlotResponseDtos);

        return rosterResponseDto;
    }
}