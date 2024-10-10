// RosterController.java
package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.GenerateEmployeeMonthRosterRequestDto;
import nl.mfarr.supernova.dtos.RosterResponseDto;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.mappers.RosterMapper;
import nl.mfarr.supernova.services.RosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @GetMapping("/monthly/{employeeId}/{month}/{year}")
    public ResponseEntity<RosterResponseDto> getEmployeeMonthlyRoster(
            @PathVariable Long employeeId,
            @PathVariable int month,
            @PathVariable int year) {
        RosterResponseDto responseDto = rosterService.getEmployeeMonthlyRoster(employeeId, month, year);
        return ResponseEntity.ok(responseDto);
    }

   /* @GetMapping("/weekly/{employeeId}/{week}/{year}")
    public RosterResponseDto getEmployeeWeeklyRoster(
            @PathVariable Long employeeId,
            @PathVariable int week,
            @PathVariable int year) {
        return rosterService.getEmployeeWeeklyRoster(employeeId, week, year);
    }*/

    @GetMapping("/daily/{employeeId}/{date}")
    public RosterResponseDto getEmployeeDailyRoster(
            @PathVariable Long employeeId,
            @PathVariable @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date) {
        return rosterService.getEmployeeDailyRoster(employeeId, date);
    }
}