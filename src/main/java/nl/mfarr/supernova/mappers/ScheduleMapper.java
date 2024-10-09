package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.ScheduleUpsertRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {
@Autowired
private EmployeeRepository employeeRepository;
    public ScheduleMapper() {
    }

    public ScheduleEntity toEntity(ScheduleUpsertRequestDto dto) {
        if (dto == null) {
            return null;
        }
        ScheduleEntity entity = new ScheduleEntity();
        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
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