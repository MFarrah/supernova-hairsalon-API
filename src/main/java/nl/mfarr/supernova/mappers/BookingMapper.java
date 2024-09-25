package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public static BookingEntity toEntity(BookingRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, BookingEntity.class);
    }

    public static BookingResponseDto toDto(BookingEntity entity) {
        return GenericMapperHelper.mapToDto(entity, BookingResponseDto.class);
    }
}
