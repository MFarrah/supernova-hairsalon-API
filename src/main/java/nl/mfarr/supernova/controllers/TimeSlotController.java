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

    // Endpoint om beschikbare tijdslots op te halen voor een medewerker op een bepaalde datum en tijd
    @GetMapping("/available")
    public ResponseEntity<List<TimeSlotResponseDto>> getAvailableTimeSlots(
            @RequestParam Long employeeId,
            @RequestParam String date,
            @RequestParam String startTime,
            @RequestParam int duration) {

        // Converteer de Strings naar LocalDate en LocalTime
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(startTime);

        // Roep de service aan met de omgezette LocalDate en LocalTime
        List<TimeSlotResponseDto> timeSlots = timeSlotService.getAvailableTimeSlots(employeeId, localDate, localTime, duration);
        return new ResponseEntity<>(timeSlots, HttpStatus.OK);
    }

    // Endpoint om een tijdslot te verwijderen (alleen admin)
    @DeleteMapping("/{timeSlotId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long timeSlotId) {
        timeSlotService.deleteTimeSlot(timeSlotId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
