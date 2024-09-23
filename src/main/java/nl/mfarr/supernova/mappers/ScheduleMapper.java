package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

@Component
public class ScheduleMapper {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Convert DTO to Entity, including fetching EmployeeEntity
    public ScheduleEntity toEntity(ScheduleRequestDto dto) {
        ScheduleEntity entity = new ScheduleEntity();

        entity.setDayOfWeek(DayOfWeek.valueOf(dto.getDayOfWeek().toUpperCase(Locale.ENGLISH)));
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());

        // Fetch EmployeeEntity via employeeId
        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        entity.setEmployee(employee);

        return entity;
    }

    // Convert Entity to ResponseDto, including fetching employeeId
    public ScheduleResponseDto toResponseDto(ScheduleEntity entity) {
        ScheduleResponseDto dto = new ScheduleResponseDto();

        dto.setScheduleId(entity.getScheduleId());
        dto.setEmployeeId(entity.getEmployee().getEmployeeId());
        dto.setDayOfWeek(entity.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setDate(entity.getDate());

        return dto;
    }
}