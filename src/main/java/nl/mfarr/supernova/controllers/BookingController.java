package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.bookingDtos.*;
import nl.mfarr.supernova.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

@PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/customer-booking")
    public ResponseEntity<BookingResponseDto> createCustomerBooking(@RequestBody BookingCustomerRequestDto requestDto, Authentication authentication) {
        BookingResponseDto responseDto = bookingService.createCustomerBooking(requestDto, authentication);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/employee-booking")
    public ResponseEntity<BookingResponseDto> createEmployeeBooking(@RequestBody BookingEmployeeRequestDto requestDto, Authentication authentication) {
        BookingResponseDto responseDto = bookingService.createEmployeeBooking(requestDto, authentication);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/all-bookings-from-date")
    public List<BookingUIGetAllResponseDto> getAllBookingsFromDate(@RequestBody BookingUIGetAllRequestDto requestDto) {
        return bookingService.getAllBookingsFromDate(requestDto);

    }

}