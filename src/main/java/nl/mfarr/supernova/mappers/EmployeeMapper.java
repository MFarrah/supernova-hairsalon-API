package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.*;
import nl.mfarr.supernova.entities.*;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeEntity toEntity(EmployeeCreateRequestDto dto) {
        EmployeeEntity entity = GenericMapperHelper.mapToEntity(dto, EmployeeEntity.class);
        if (dto.getWorkingSchedule() != null) {
            entity.setWorkingSchedule(dto.getWorkingSchedule().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toSet()));
        }
        return entity;
    }

    public EmployeeEntity toEntity(EmployeeUpdateRequestDto dto) {
        EmployeeEntity entity = GenericMapperHelper.mapToEntity(dto, EmployeeEntity.class);
        if (dto.getWorkingSchedule() != null) {
            entity.setWorkingSchedule(dto.getWorkingSchedule().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toSet()));
        }
        return entity;
    }

    public EmployeeResponseDto toDto(EmployeeEntity employeeEntity) {
        EmployeeResponseDto employeeResponseDto = GenericMapperHelper.mapToDto(employeeEntity, EmployeeResponseDto.class);
        if (employeeEntity.getWorkingSchedule() != null) {
            employeeResponseDto.setWorkingSchedule(employeeEntity.getWorkingSchedule().stream()
                    .map(this::toDto)
                    .collect(Collectors.toSet()));
        }
        return employeeResponseDto;
    }

    public ScheduleEntity toEntity(ScheduleCreateRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, ScheduleEntity.class);
    }

    public ScheduleResponseDto toDto(ScheduleEntity entity) {
        return GenericMapperHelper.mapToDto(entity, ScheduleResponseDto.class);
    }
}