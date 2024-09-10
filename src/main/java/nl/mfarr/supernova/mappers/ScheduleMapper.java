package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;

public class ScheduleMapper {

    public static ScheduleEntity toEntity(ScheduleRequestDto dto, EmployeeEntity employee) {
        ScheduleEntity entity = new ScheduleEntity();
        entity.setEmployee(employee);
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        return entity;
    }

    public static ScheduleResponseDto toResponseDto(ScheduleEntity entity) {
        ScheduleResponseDto dto = new ScheduleResponseDto();
        dto.setScheduleId(entity.getScheduleId());
        dto.setEmployeeId(entity.getEmployee().getEmployeeId());
        dto.setDayOfWeek(entity.getDayOfWeek());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }
}
