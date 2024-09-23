package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.TimeSlotRequestDto;
import nl.mfarr.supernova.dtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotMapper {

    // Convert DTO to Entity
    public TimeSlotEntity toEntity(TimeSlotRequestDto requestDto) {
        TimeSlotEntity entity = new TimeSlotEntity();
        entity.setDate(requestDto.getDate());
        entity.setStartTime(requestDto.getStartTime());
        entity.setEndTime(requestDto.getEndTime());
        return entity;
    }

    // Convert Entity to ResponseDto
    public TimeSlotResponseDto toDto(TimeSlotEntity entity) {
        TimeSlotResponseDto dto = new TimeSlotResponseDto();
        dto.setTimeSlotId(entity.getTimeSlotId());
        dto.setDate(entity.getDate());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }
}
