package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.ScheduleUpsertRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {


    public ScheduleMapper() {
    }



    public ScheduleEntity toEntity(ScheduleUpsertRequestDto dto, EmployeeEntity employee) {
        if (dto == null) {
            return null;
        }
        ScheduleEntity entity = new ScheduleEntity();
        entity.setEmployee(employee);
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
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setDayOfWeek(entity.getDayOfWeek());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }
}