package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.BookingCustomerRequestDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.mappers.BookingMapper;
import nl.mfarr.supernova.repositories.BookingRepository;
import nl.mfarr.supernova.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("#bookingRequest.customerId == authentication.principal.id")
    @PostMapping("/customer-booking")
    public ResponseEntity<BookingCustomerRequestDto> createCustomerBooking(@RequestBody BookingCustomerRequestDto bookingRequest) {
        if (bookingRequest.getDate() == null || bookingRequest.getStartTime() == null) {
            return ResponseEntity.badRequest().body(null); // Return bad request if date or start time is null
        }
        BookingEntity savedBooking = bookingRepository.save(bookingMapper.toEntity(bookingRequest));
        return ResponseEntity.ok(bookingMapper.toDto(savedBooking));
    }
}
