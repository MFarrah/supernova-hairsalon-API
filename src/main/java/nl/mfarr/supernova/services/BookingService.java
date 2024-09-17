package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.mappers.BookingMapper;
import nl.mfarr.supernova.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMapper bookingMapper;

    public List<BookingResponseDto> getBookingsByCustomerAndStatus(Long customerId, BookingStatus status) {
        return bookingRepository.findByCustomerCustomerIdAndStatus(customerId, status)  // Gebruik de repository methode
                .stream()
                .map(bookingMapper::toResponseDto)
                .toList();
    }

    public List<BookingResponseDto> getBookingsByEmployeeAndStatus(Long employeeId, BookingStatus status) {
        return bookingRepository.findByEmployeeEmployeeIdAndStatus(employeeId, status)  // Gebruik de repository methode
                .stream()
                .map(bookingMapper::toResponseDto)
                .toList();
    }
}
