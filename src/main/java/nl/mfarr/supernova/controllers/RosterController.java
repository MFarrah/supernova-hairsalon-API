// RosterController.java
package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.rosterDtos.GenerateRosterRequestDto;
import nl.mfarr.supernova.services.RosterService;
import nl.mfarr.supernova.services.TimeSlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> generateRoster(@RequestBody GenerateRosterRequestDto request) {
        rosterService.createRoster(request.getEmployeeId(), request.getYear(), request.getMonth());
        return ResponseEntity.ok("Monthly roster generated successfully");
    }



}

