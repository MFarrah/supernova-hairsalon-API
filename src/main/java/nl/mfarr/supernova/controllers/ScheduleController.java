package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto scheduleResponse = scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity.ok(scheduleResponse);
    }

    @GetMapping("/employee/{employeeId}/day/{dayOfWeek}")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedulesByEmployeeAndDay(
            @PathVariable Long employeeId,
            @PathVariable DayOfWeek dayOfWeek) {
        List<ScheduleResponseDto> schedules = scheduleService.getSchedulesByEmployeeAndDay(employeeId, dayOfWeek);
        return ResponseEntity.ok(schedules);
    }
}
