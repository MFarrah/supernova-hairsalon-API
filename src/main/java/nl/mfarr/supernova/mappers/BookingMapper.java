package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.models.BookingEntity;
import nl.mfarr.supernova.repositories.CustomerRepository;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public BookingResponseDto toDto(BookingEntity bookingEntity) {
        BookingResponseDto bookingDto = new BookingResponseDto();
        bookingDto.setBookingId(bookingEntity.getBookingId());
        bookingDto.setDate(bookingEntity.getDate());
        bookingDto.setTime(bookingEntity.getTime());
        bookingDto.setStatus(bookingEntity.getStatus());
        bookingDto.setCustomerId(bookingEntity.getCustomer().getCustomerId());
        bookingDto.setEmployeeId(bookingEntity.getEmployee().getEmployeeId());
        return bookingDto;
    }

    public BookingEntity toEntity(BookingRequestDto bookingDto) {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setDate(bookingDto.getDate());
        bookingEntity.setTime(bookingDto.getTime());
        bookingEntity.setStatus(bookingDto.getStatus());
        bookingEntity.setCustomer(customerRepository.findById(bookingDto.getCustomerId()).orElse(null));
        bookingEntity.setEmployee(employeeRepository.findById(bookingDto.getEmployeeId()).orElse(null));
        return bookingEntity;
    }
}
