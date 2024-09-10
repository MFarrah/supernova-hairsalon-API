package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
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
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto requestDto) {
        return ResponseEntity.ok(bookingService.createBooking(requestDto));
    }
}
