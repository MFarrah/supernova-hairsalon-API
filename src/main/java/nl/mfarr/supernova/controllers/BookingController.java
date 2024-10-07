package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.BookingCustomerRequestDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.exceptions.CustomerMismatchException;
import nl.mfarr.supernova.exceptions.FailedToSaveException;
import nl.mfarr.supernova.exceptions.InvalidBookingRequestException;
import nl.mfarr.supernova.mappers.BookingMapper;
import nl.mfarr.supernova.repositories.BookingRepository;
import nl.mfarr.supernova.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingController(BookingRepository bookingRepository, BookingService bookingService, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    @PostMapping("/customer-booking")
    public ResponseEntity<BookingCustomerRequestDto> createCustomerBooking(@RequestBody BookingCustomerRequestDto bookingRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long loggedInCustomerId = (Long) authentication.getPrincipal();

        if (!bookingRequest.getCustomerId().equals(loggedInCustomerId)) {
            throw new CustomerMismatchException("Customer ID does not match the logged-in user");
        }

        if (bookingRequest.getDate() == null || bookingRequest.getStartTime() == null) {
            throw new InvalidBookingRequestException("Date and start time are required");
        }

        BookingEntity savedBooking = bookingRepository.save(bookingMapper.toEntity(bookingRequest));
        if (savedBooking == null) {
            throw new FailedToSaveException("Failed to save the booking");
        }
        return ResponseEntity.ok(bookingMapper.toDto(savedBooking));
    }
}

