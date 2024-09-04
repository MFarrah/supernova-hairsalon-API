package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.mappers.BookingMapper;
import nl.mfarr.supernova.models.BookingEntity;
import nl.mfarr.supernova.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMapper bookingMapper;

    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<BookingResponseDto> getBookingById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::toDto);
    }

    public BookingResponseDto createBooking(BookingRequestDto bookingDto) {
        BookingEntity bookingEntity = bookingMapper.toEntity(bookingDto);
        BookingEntity savedEntity = bookingRepository.save(bookingEntity);
        return bookingMapper.toDto(savedEntity);
    }

    public BookingResponseDto updateBooking(BookingRequestDto bookingDto) {
        BookingEntity bookingEntity = bookingMapper.toEntity(bookingDto);
        BookingEntity updatedEntity = bookingRepository.save(bookingEntity);
        return bookingMapper.toDto(updatedEntity);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
