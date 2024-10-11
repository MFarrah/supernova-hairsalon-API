package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.rosterDtos.GenerateMonthRosterRequestDto;
import nl.mfarr.supernova.dtos.timeSlotDtos.CustomRosterRequestDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.*;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class RosterService {

    @Autowired
    private RosterRepository rosterRepository;

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


    public void createCustomRoster(CustomRosterRequestDto customRosterRequestDto) {

        // Haal de employee op of gooi een fout als deze niet bestaat
        EmployeeEntity employee = employeeRepository.findById(customRosterRequestDto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        // Haal bestaande tijdslots op binnen het gegeven tijdsbereik customRosterRequestDto.getDate(), customRosterRequestDto.getEventStartTime(), customRosterRequestDto.getEventEndTime());
        List<TimeSlotEntity> timeSlots = timeSlotRepository.findByEmployeeAndDate(employee, customRosterRequestDto.getDate());


        // Controleer of er overlappingen zijn met bestaande tijdslots
        for (TimeSlotEntity timeSlot : timeSlots) {
            if (timeSlot.getStartTime().isBefore(customRosterRequestDto.getEventEndTime()) &&
                    timeSlot.getEndTime().isAfter(customRosterRequestDto.getEventStartTime())) {
                throw new IllegalArgumentException("Timeslots in range are already booked");
            }
        }

        // Controleer of de starttijd correct is
        if (customRosterRequestDto.getEventStartTime().isAfter(customRosterRequestDto.getEventEndTime())) {
            throw new IllegalArgumentException("Event start time cannot be after event end time");
        }

        // Controleer of de status van het tijdslot correct is
        if (customRosterRequestDto.getStatus() != TimeSlotStatus.AVAILABLE) {
            throw new IllegalArgumentException("Invalid status");
        }

        // Haal de roster op voor de betreffende werknemer, maand en jaar
        List<RosterEntity> optionalRoster = rosterRepository.findByEmployeeAndYearAndMonth(employee,
                customRosterRequestDto.getDate().getYear(),
                customRosterRequestDto.getDate().getMonthValue());
        RosterEntity roster = optionalRoster.isEmpty() ? new RosterEntity() : optionalRoster.get(0);

        // Verwijder bestaande tijdslots voor de opgegeven datum
        List<TimeSlotEntity> existingTimeSlots = timeSlotRepository.findByRosterAndDate(roster, customRosterRequestDto.getDate());
        timeSlotRepository.deleteAll(existingTimeSlots);

        // Maak nieuwe tijdslots gebaseerd op de input
        List<TimeSlotEntity> newTimeSlots = new ArrayList<>();
        LocalTime startTime = customRosterRequestDto.getEventStartTime();
        LocalTime endTime = customRosterRequestDto.getEventEndTime();

        // Maak tijdslots van 15 minuten aan tussen de start- en eindtijd
        while (startTime.isBefore(endTime)) {
            TimeSlotEntity timeSlot = new TimeSlotEntity();
            timeSlot.setEmployee(employee);
            timeSlot.setRoster(roster);
            timeSlot.setDate(customRosterRequestDto.getDate());
            timeSlot.setStartTime(startTime);
            timeSlot.setEndTime(startTime.plusMinutes(15));
            timeSlot.setStatus(TimeSlotStatus.AVAILABLE);  // Zorg ervoor dat status correct is
            newTimeSlots.add(timeSlot);
            startTime = startTime.plusMinutes(15);
        }

        // Sla de nieuwe tijdslots op en werk de roster bij
        timeSlotRepository.saveAll(newTimeSlots);
        roster.setTimeSlots(newTimeSlots);
        rosterRepository.save(roster);
    }

}





