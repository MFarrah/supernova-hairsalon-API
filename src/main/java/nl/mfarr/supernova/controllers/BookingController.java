package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.BookingCustomerRequestDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.mappers.BookingMapper;
import nl.mfarr.supernova.repositories.BookingRepository;
import nl.mfarr.supernova.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<BookingCustomerRequestDto> createBooking(@RequestBody BookingCustomerRequestDto bookingRequest) {
        BookingEntity savedBooking = bookingRepository.save(bookingMapper.toEntity(bookingRequest));
        return ResponseEntity.ok(bookingMapper.toDto(savedBooking));
    }
}
