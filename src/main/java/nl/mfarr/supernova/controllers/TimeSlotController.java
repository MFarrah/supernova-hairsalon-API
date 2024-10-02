package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.TimeSlotResponseDto;
import nl.mfarr.supernova.services.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/timeslots")
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{timeSlotId}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long timeSlotId) {
        timeSlotService.deleteTimeSlot(timeSlotId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{employeeId}/{date}")
    public ResponseEntity<List<Object>> getTimeSlotsByEmployeeIdAndDate(@PathVariable Long employeeId, @PathVariable String date) {
        return new ResponseEntity<>(timeSlotService.getTimeSlotsByEmployeeIdAndDate(employeeId, LocalDate.parse(date)), HttpStatus.OK);
    }


}
