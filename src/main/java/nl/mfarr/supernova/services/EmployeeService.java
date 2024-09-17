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

    // Maak een nieuwe medewerker aan
    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeRequestDto);  // Gebruik mapper voor conversie
        employeeEntity = employeeRepository.save(employeeEntity);
        return employeeMapper.toResponseDto(employeeEntity);
    }

    // Zoek naar een medewerker op basis van e-mail
    public Optional<EmployeeResponseDto> findByEmail(String email) {
        return employeeRepository.findByEmail(email).map(employeeMapper::toResponseDto);
    }

    // Controleer of een e-mail al bestaat
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    // Zoek naar medewerkers die beschikbaar zijn op een bepaalde dag en tijd
    public List<EmployeeResponseDto> findByAvailability(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return employeeRepository.findBySchedules_DayOfWeekAndSchedules_StartTimeBeforeAndSchedules_EndTimeAfter(
                        dayOfWeek, startTime, endTime)
                .stream()
                .map(employeeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    // Haal een EmployeeEntity op via ID
    public EmployeeEntity getEmployeeEntityById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalStateException("Employee not found with ID: " + employeeId));
    }
}
