package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/customer/{customerId}")
    public List<BookingResponseDto> getBookingsByCustomerAndStatus(
            @PathVariable Long customerId,
            @RequestParam BookingStatus status) {  // Status als parameter
        return bookingService.getBookingsByCustomerAndStatus(customerId, status);
    }

    @GetMapping("/employee/{employeeId}")
    public List<BookingResponseDto> getBookingsByEmployeeAndStatus(
            @PathVariable Long employeeId,
            @RequestParam BookingStatus status) {  // Status als parameter
        return bookingService.getBookingsByEmployeeAndStatus(employeeId, status);
    }
}
