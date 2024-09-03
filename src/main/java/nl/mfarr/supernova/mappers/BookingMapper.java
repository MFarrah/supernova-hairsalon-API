package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.models.BookingEntity;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

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
        return bookingEntity;
    }
}
