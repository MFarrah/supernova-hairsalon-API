package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.mappers.BookingMapper;
import nl.mfarr.supernova.repositories.BookingRepository;
import nl.mfarr.supernova.repositories.CustomerRepository;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public BookingResponseDto createBooking(BookingRequestDto dto) {
        CustomerEntity customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));
        BookingEntity entity = BookingMapper.toEntity(dto, customer, employee);
        BookingEntity savedEntity = bookingRepository.save(entity);
        return BookingMapper.toResponseDto(savedEntity);
    }

    public BookingResponseDto getBookingById(Long id) {
        BookingEntity entity = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        return BookingMapper.toResponseDto(entity);
    }

    public List<BookingResponseDto> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(BookingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public BookingResponseDto updateBooking(Long id, BookingRequestDto dto) {
        BookingEntity entity = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
        CustomerEntity customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));
        entity.setDate(dto.getDate());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setCustomer(customer);
        entity.setEmployee(employee);
        BookingEntity updatedEntity = bookingRepository.save(entity);
        return BookingMapper.toResponseDto(updatedEntity);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}