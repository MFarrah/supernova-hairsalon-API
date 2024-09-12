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

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public BookingResponseDto createBooking(BookingRequestDto requestDto) {
        CustomerEntity customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        EmployeeEntity employee = employeeRepository.findById(requestDto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        BookingEntity booking = BookingMapper.toEntity(requestDto, customer, employee);
        BookingEntity savedBooking = bookingRepository.save(booking);

        return BookingMapper.toResponseDto(savedBooking);
    }
}
