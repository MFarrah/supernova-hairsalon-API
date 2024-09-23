package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Converteer DTO naar Entity, inclusief het ophalen van de EmployeeEntity
    public ScheduleEntity toEntity(ScheduleRequestDto dto) {
        ScheduleEntity entity = new ScheduleEntity();
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());

        // Haal de EmployeeEntity op met behulp van employeeId
        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Medewerker niet gevonden"));
        entity.setEmployee(employee);  // Zet de EmployeeEntity in het ScheduleEntity

        return entity;
    }

    // Converteer Entity naar ResponseDto, inclusief het ophalen van employeeId
    public ScheduleResponseDto toResponseDto(ScheduleEntity entity) {
        ScheduleResponseDto dto = new ScheduleResponseDto();
        dto.setScheduleId(entity.getScheduleId());
        dto.setEmployeeId(entity.getEmployee().getEmployeeId());  // Haal de employeeId op vanuit de EmployeeEntity
        dto.setDayOfWeek(entity.getDayOfWeek());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }
}
