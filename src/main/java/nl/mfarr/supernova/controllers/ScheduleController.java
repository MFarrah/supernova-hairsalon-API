package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // Endpoint om een nieuw rooster aan te maken (alleen admin)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto schedule = scheduleService.createSchedule(scheduleRequestDto);
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    // Endpoint om roosters van een medewerker op te halen
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedulesByEmployee(@PathVariable Long employeeId) {
        List<ScheduleResponseDto> schedules = scheduleService.getSchedulesForEmployee(employeeId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // Endpoint om een rooster te verwijderen (alleen admin)
    @DeleteMapping("/{scheduleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint om beschikbaarheid van een medewerker te controleren
    @GetMapping("/availability")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
    public ResponseEntity<Boolean> isEmployeeAvailable(
            @RequestParam Long employeeId,
            @RequestParam String dayOfWeek,
            @RequestParam String startTime,
            @RequestParam String endTime) {
        DayOfWeek dayOfWeekEnum = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
        LocalTime startTimeParsed = LocalTime.parse(startTime);
        LocalTime endTimeParsed = LocalTime.parse(endTime);

        boolean available = scheduleService.isEmployeeAvailable(employeeId, dayOfWeekEnum, startTimeParsed, endTimeParsed);
        return new ResponseEntity<>(available, HttpStatus.OK);
    }
}