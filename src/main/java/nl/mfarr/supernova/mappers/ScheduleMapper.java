package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.ScheduleUpsertRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public ScheduleEntity toEntity(ScheduleUpsertRequestDto dto, EmployeeEntity employee) {
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setEmployee(employee);  // Koppel de employee
        return schedule;
    }

    public ScheduleResponseDto toDto(ScheduleEntity schedule) {
        ScheduleResponseDto dto = new ScheduleResponseDto();
        dto.setId(schedule.getId());
        dto.setEmployeeId(schedule.getEmployee().getId());
        dto.setDayOfWeek(schedule.getDayOfWeek());
        dto.setStartTime(schedule.getStartTime());
        dto.setEndTime(schedule.getEndTime());
        return dto;
    }
}
