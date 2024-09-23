package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.entities.BookingEntity;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingEntity toEntity(BookingRequestDto dto) {
        BookingEntity entity = new BookingEntity();
        entity.setBookingDate(dto.getDate());
        entity.setStartTime(dto.getStartTime());
        return entity;
    }

    public BookingResponseDto toResponseDto(BookingEntity entity) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setBookingId(entity.getBookingId());
        dto.setCustomerId(entity.getCustomer().getCustomerId());
        dto.setEmployeeId(entity.getEmployee().getEmployeeId());
        dto.setDate(entity.getBookingDate());
        dto.setStartTime(entity.getStartTime());
        dto.setTotalCost(entity.getTotalCost());
        dto.setTotalDuration(entity.getTotalDuration());
        dto.setStatus(entity.getStatus().name());
        return dto;
    }
}
