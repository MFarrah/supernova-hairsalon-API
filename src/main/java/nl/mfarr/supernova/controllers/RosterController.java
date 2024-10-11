// RosterController.java
package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.timeSlotDtos.EmployeeMonthRequestDto;
import nl.mfarr.supernova.dtos.rosterDtos.GenerateMonthRosterRequestDto;
import nl.mfarr.supernova.dtos.rosterDtos.RosterResponseDto;
import nl.mfarr.supernova.dtos.timeSlotDtos.TimeSlotResponseDto;
import nl.mfarr.supernova.services.RosterService;
import nl.mfarr.supernova.services.TimeSlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rosters")
public class RosterController {

    private final RosterService rosterService;
    private final TimeSlotService timeSlotService;

    public RosterController(RosterService rosterService, TimeSlotService timeSlotService) {
        this.rosterService = rosterService;
        this.timeSlotService = timeSlotService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generate-roster")
    public ResponseEntity<String> generateRoster(@RequestBody GenerateMonthRosterRequestDto request) {
        rosterService.createRoster(request);
        return ResponseEntity.ok("Monthly roster generated successfully");
    }

    @GetMapping("/month")
    public ResponseEntity<List<TimeSlotResponseDto>> getEmployeeMonthRoster(@RequestBody EmployeeMonthRequestDto request) {
        List<TimeSlotResponseDto> response = timeSlotService.getEmployeeMonthRoster(request);
        return ResponseEntity.ok(response);
    }

}

