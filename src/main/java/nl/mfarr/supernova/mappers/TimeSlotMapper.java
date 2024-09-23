package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.TimeSlotRequestDto;
import nl.mfarr.supernova.dtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotMapper {

    public TimeSlotEntity toEntity(TimeSlotRequestDto dto) {
        TimeSlotEntity entity = new TimeSlotEntity();
        entity.setDate(dto.getDate());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        // Employee moet worden opgehaald en ingesteld via de service
        return entity;
    }

    public TimeSlotResponseDto toDto(TimeSlotEntity entity) {
        TimeSlotResponseDto dto = new TimeSlotResponseDto();
        dto.setTimeSlotId(entity.getTimeSlotId());
        dto.setEmployeeId(entity.getEmployee().getEmployeeId());
        dto.setDate(entity.getDate());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }
}
