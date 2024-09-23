package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Endpoint om een nieuwe boeking te maken
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        BookingResponseDto booking = bookingService.createBooking(bookingRequestDto);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    // Endpoint om boekingen op te halen van een klant
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByCustomer(@PathVariable Long customerId) {
        List<BookingResponseDto> bookings = bookingService.getBookingsByCustomer(customerId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Endpoint om boekingen op te halen van een medewerker
    @GetMapping("/employee/{employeeId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponseDto>> getBookingsByEmployee(@PathVariable Long employeeId) {
        List<BookingResponseDto> bookings = bookingService.getBookingsByEmployee(employeeId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Endpoint om een boeking te annuleren (alleen door klant, medewerker of admin)
    @PutMapping("/cancel/{bookingId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<BookingResponseDto> cancelBooking(@PathVariable Long bookingId) {
        BookingResponseDto booking = bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    // Endpoint om een boeking status te updaten door een medewerker of admin
    @PutMapping("/update-status/{bookingId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<BookingResponseDto> updateBookingStatus(@PathVariable Long bookingId, @RequestParam String status) {
        BookingResponseDto booking = bookingService.updateBookingStatus(bookingId, status);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
}
