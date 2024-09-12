package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.entities.BookingEntity;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingEntity toEntity(BookingRequestDto bookingRequestDto) {
        BookingEntity bookingEntity = new BookingEntity();
        // Assume that customer and employee entities will be fetched via service layers
        bookingEntity.setDate(bookingRequestDto.getDate());
        bookingEntity.setStartTime(bookingRequestDto.getStartTime());
        bookingEntity.setEndTime(bookingRequestDto.getEndTime());
        // Orders should be set via a service that fetches them
        return bookingEntity;
    }

    public BookingResponseDto toResponseDto(BookingEntity bookingEntity) {
        BookingResponseDto responseDto = new BookingResponseDto();
        responseDto.setBookingId(bookingEntity.getBookingId());
        responseDto.setCustomerId(bookingEntity.getCustomer().getCustomerId());
        responseDto.setEmployeeId(bookingEntity.getEmployee().getEmployeeId());
        responseDto.setDate(bookingEntity.getDate());
        responseDto.setStartTime(bookingEntity.getStartTime());
        responseDto.setEndTime(bookingEntity.getEndTime());
        responseDto.setTotalDuration(bookingEntity.getTotalDuration());
        responseDto.setTotalCost(bookingEntity.getTotalCost());
        return responseDto;
    }
}
