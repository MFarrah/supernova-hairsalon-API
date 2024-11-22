package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.mappers.BookingMapper;
import nl.mfarr.supernova.repositories.BookingRepository;
import nl.mfarr.supernova.enums.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMapper bookingMapper;

    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto) {
        BookingEntity bookingEntity = bookingMapper.toEntity(bookingRequestDto);
        bookingEntity = bookingRepository.save(bookingEntity);
        return bookingMapper.toResponseDto(bookingEntity);
    }

    public List<BookingResponseDto> getBookingsByCustomer(Long customerId, BookingStatus status) {
        return bookingRepository.findByCustomerCustomerIdAndStatus(customerId, status)
                .stream()
                .map(bookingMapper::toResponseDto)
                .toList();
    }

    public List<BookingResponseDto> getBookingsByEmployee(Long employeeId, BookingStatus status) {
        return bookingRepository.findByEmployeeEmployeeIdAndStatus(employeeId, status)
                .stream()
                .map(bookingMapper::toResponseDto)
                .toList();
    }

    public List<BookingResponseDto> getBookingsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByDateBetween(startDate, endDate)
                .stream()
                .map(bookingMapper::toResponseDto)
                .toList();
    }
}
