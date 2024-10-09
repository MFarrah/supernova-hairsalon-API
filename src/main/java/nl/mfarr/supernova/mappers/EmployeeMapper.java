package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.*;
import nl.mfarr.supernova.entities.*;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    @Autowired
    private EmployeeRepository employeeRepository;


    public EmployeeMapper() {
    }

    public EmployeeEntity toEntity(EmployeeUpsertRequestDto dto) {
        if (dto == null) {
            return null;
        }
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setGender(dto.getGender());
        entity.setRoles(dto.getRoles());
        entity.setQualifiedOrderIds(dto.getQualifiedOrderIds());

        if (dto.getWorkingSchedule() != null) {
            entity.setWorkingSchedule(dto.getWorkingSchedule().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toSet()));
        }
        return entity;
    }



    public EmployeeResponseDto toDto(EmployeeEntity employeeEntity) {
        if (employeeEntity == null) {
            return null;
        }
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(employeeEntity.getId());
        dto.setEmail(employeeEntity.getEmail());
        dto.setFirstName(employeeEntity.getFirstName());
        dto.setLastName(employeeEntity.getLastName());
        dto.setDateOfBirth(employeeEntity.getDateOfBirth());
        dto.setPhoneNumber(employeeEntity.getPhoneNumber());
        dto.setGender(employeeEntity.getGender());
        dto.setRoles(employeeEntity.getRoles());
        dto.setQualifiedOrderIds(employeeEntity.getQualifiedOrderIds());
        if (employeeEntity.getWorkingSchedule() != null) {
            dto.setWorkingSchedule(employeeEntity.getWorkingSchedule().stream()
                    .map(this::toDto)
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    public ScheduleEntity toEntity(ScheduleUpsertRequestDto dto) {
        if (dto == null) {
            return null;
        }
        ScheduleEntity entity = new ScheduleEntity();
        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(() -> new EmployeeNotFoundException("Employee not found by id: " + dto.getEmployeeId()));
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