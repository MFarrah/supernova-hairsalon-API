// RosterController.java
package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.GenerateEmployeeMonthRosterRequestDto;
import nl.mfarr.supernova.dtos.RosterResponseDto;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.mappers.RosterMapper;
import nl.mfarr.supernova.services.RosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rosters")
public class RosterController {

    private final RosterService rosterService;
    private final RosterMapper rosterMapper;

    @Autowired
    public RosterController(RosterService rosterService, RosterMapper rosterMapper) {
        this.rosterService = rosterService;
        this.rosterMapper = rosterMapper;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/generate")
    public ResponseEntity<RosterResponseDto> generateMonthlyRoster(@RequestBody GenerateEmployeeMonthRosterRequestDto requestDto) {
        RosterEntity roster = rosterService.generateMonthlyRoster(requestDto);
        RosterResponseDto responseDto = rosterMapper.toDto(roster);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{employeeId}/month/{month}")
    public ResponseEntity<RosterResponseDto> getEmployeeMonthlyRoster(@PathVariable Long employeeId, @PathVariable int month) {
        RosterResponseDto responseDto = rosterService.getEmployeeMonthlyRoster(employeeId, month);
        return ResponseEntity.ok(responseDto);
    }
}