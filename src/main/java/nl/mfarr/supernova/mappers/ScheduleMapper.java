package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.ScheduleCreateRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.dtos.ScheduleUpdateRequestDto;
import nl.mfarr.supernova.entities.ScheduleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {


    public ScheduleMapper() {
    }

    public ScheduleEntity toEntity(ScheduleCreateRequestDto dto) {
        if (dto == null) {
            return null;
        }
        ScheduleEntity entity = new ScheduleEntity();
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        return entity;
    }

    public ScheduleEntity toEntity(ScheduleUpdateRequestDto dto) {
        if (dto == null) {
            return null;
        }
        ScheduleEntity entity = new ScheduleEntity();
        entity.setId(dto.getId());
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        return entity;
    }

    public ScheduleResponseDto toDto(ScheduleEntity entity) {
        if (entity == null) {
            return null;
        }
        ScheduleResponseDto dto = new ScheduleResponseDto();
        dto.setId(entity.getId());
        dto.setDayOfWeek(entity.getDayOfWeek());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }
}