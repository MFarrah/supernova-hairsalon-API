package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.mappers.EmployeeMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;


    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        // Convert EmployeeRequestDto to EmployeeEntity, save it, and convert to EmployeeResponseDto
        EmployeeEntity employeeEntity = new EmployeeEntity();
        // Set properties from employeeRequestDto to employeeEntity
        employeeEntity = employeeRepository.save(employeeEntity);
        return toResponseDto(employeeEntity);
    }

    public Optional<EmployeeResponseDto> findByEmail(String email) {
        return employeeRepository.findByEmail(email).map(this::toResponseDto);
    }

    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public List<EmployeeResponseDto> findByAvailability(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return employeeRepository.findBySchedules_DayOfWeekAndSchedules_StartTimeBeforeAndSchedules_EndTimeAfter(
                dayOfWeek, startTime, endTime).stream().map(employeeMapper::toResponseDto).collect(Collectors.toList());
    }

    public EmployeeResponseDto toResponseDto(EmployeeEntity employeeEntity) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        // Set properties from employeeEntity to dto
        return dto;
    }

    // Nieuwe methode om EmployeeEntity op te halen via ID
    public EmployeeEntity getEmployeeEntityById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("Employee not found with ID: " + employeeId));
    }
}
