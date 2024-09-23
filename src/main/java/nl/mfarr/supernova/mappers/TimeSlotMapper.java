package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.TimeSlotRequestDto;
import nl.mfarr.supernova.dtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotMapper {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Converteer DTO naar Entity, inclusief het ophalen van de EmployeeEntity
    public TimeSlotEntity toEntity(TimeSlotRequestDto requestDto) {
        TimeSlotEntity entity = new TimeSlotEntity();
        entity.setDate(requestDto.getDate());
        entity.setStartTime(requestDto.getStartTime());
        entity.setEndTime(requestDto.getEndTime());

        // Haal EmployeeEntity op met behulp van employeeId
        EmployeeEntity employee = employeeRepository.findById(requestDto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Medewerker niet gevonden"));
        entity.setEmployee(employee);

        return entity;
    }

    // Converteer Entity naar ResponseDto, inclusief het ophalen van employeeId
    public TimeSlotResponseDto toDto(TimeSlotEntity entity) {
        TimeSlotResponseDto dto = new TimeSlotResponseDto();
        dto.setTimeSlotId(entity.getTimeSlotId());
        dto.setEmployeeId(entity.getEmployee().getEmployeeId());  // Gebruik de employeeId van EmployeeEntity
        dto.setDate(entity.getDate());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        return dto;
    }
}
