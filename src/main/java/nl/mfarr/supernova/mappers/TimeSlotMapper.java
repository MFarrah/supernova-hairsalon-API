package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.TimeSlotRequestDto;
import nl.mfarr.supernova.dtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class TimeSlotMapper {

    // Convert DTO to Entity
    public static TimeSlotEntity toEntity(Object dto) {
        TimeSlotEntity entity = GenericMapperHelper.mapToEntity(dto, TimeSlotEntity.class);

        // Assuming the DTO has a LocalDateTime or a String for start and end times
        // You need to split LocalDateTime into LocalDate and LocalTime
        if (dto instanceof TimeSlotRequestDto) {
            TimeSlotRequestDto timeSlotDto = (TimeSlotRequestDto) dto;
            if (timeSlotDto.getStartTime() != null) {
                LocalDateTime startDateTime = LocalDateTime.from(timeSlotDto.getStartTime());
                LocalDate date = startDateTime.toLocalDate();
                LocalTime startTime = startDateTime.toLocalTime();
                entity.setDate(date);
                entity.setStartTime(startTime);
            }
            if (timeSlotDto.getEndTime() != null) {
                LocalDateTime endDateTime = LocalDateTime.from(timeSlotDto.getEndTime());
                entity.setEndTime(endDateTime.toLocalTime());
            }
        }
        return entity;
    }

    // Convert Entity to DTO
    public static Object toDto(TimeSlotEntity entity, Class<?> dtoClass) {
        Object dto = GenericMapperHelper.mapToDto(entity, dtoClass);

        // Assuming the DTO has LocalDateTime or String for start and end times
        if (dto instanceof TimeSlotResponseDto) {
            TimeSlotResponseDto timeSlotDto = (TimeSlotResponseDto) dto;

            // Combine LocalDate and LocalTime into LocalDateTime for the DTO
            LocalDate date = entity.getDate();
            LocalTime startTime = entity.getStartTime();
            LocalTime endTime = entity.getEndTime();

            if (date != null && startTime != null) {
                timeSlotDto.setStartTime(LocalDateTime.of(date, startTime));
            }
            if (date != null && endTime != null) {
                timeSlotDto.setEndTime(LocalDateTime.of(date, endTime));
            }
        }

        return dto;
    }
}