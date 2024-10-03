package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.services.EmployeeService;
import nl.mfarr.supernova.services.RosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rosters")
public class RosterController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    RosterService rosterService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/employee/{id}/generate-roster")
    public ResponseEntity<String> generateRosterForEmployee(@PathVariable Long id) {
        rosterService.generateAndSaveRosterForEmployee(id);
        return ResponseEntity.ok("Monthly roster generated for employee.");
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/employee/{id}/roster")
    public ResponseEntity<String> getRosterForEmployee(@PathVariable Long id) {
        String roster = rosterService.getRosterForEmployee(id);
        if (roster.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roster);
    }


}
