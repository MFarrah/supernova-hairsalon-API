package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        BookingResponseDto bookingResponse = bookingService.createBooking(bookingRequestDto);
        return ResponseEntity.ok(bookingResponse);
    }

    @GetMapping("/customer/{customerId}/status/{status}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByCustomer(
            @PathVariable Long customerId,
            @PathVariable BookingStatus status) {
        List<BookingResponseDto> bookings = bookingService.getBookingsByCustomer(customerId, status);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/employee/{employeeId}/status/{status}")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByEmployee(
            @PathVariable Long employeeId,
            @PathVariable BookingStatus status) {
        List<BookingResponseDto> bookings = bookingService.getBookingsByEmployee(employeeId, status);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/dates")
    public ResponseEntity<List<BookingResponseDto>> getBookingsBetweenDates(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<BookingResponseDto> bookings = bookingService.getBookingsBetweenDates(startDate, endDate);
        return ResponseEntity.ok(bookings);
    }
}
