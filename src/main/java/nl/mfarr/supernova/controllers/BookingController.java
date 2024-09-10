package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        bookingService.createBooking(bookingRequestDto);
        return ResponseEntity.ok("Booking created successfully!");
    }

    @GetMapping("/view/{bookingId}")
    public ResponseEntity<?> viewBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.viewBooking(bookingId));
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking canceled successfully!");
    }
}
