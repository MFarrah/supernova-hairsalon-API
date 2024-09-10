package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.ok("Schedule created successfully!");
    }

    @PutMapping("/update/{scheduleId}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long scheduleId, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        scheduleService.updateSchedule(scheduleId, scheduleRequestDto);
        return ResponseEntity.ok("Schedule updated successfully!");
    }

    @GetMapping("/view/{scheduleId}")
    public ResponseEntity<?> viewSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.viewSchedule(scheduleId));
    }
}
