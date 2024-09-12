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

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        if (employeeRepository.existsByEmail(employeeRequestDto.getEmail())) {
            throw new IllegalStateException("Email already in use.");
        }

        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeRequestDto);
        employeeEntity = employeeRepository.save(employeeEntity);
        return employeeMapper.toResponseDto(employeeEntity);
    }

    public Optional<EmployeeResponseDto> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .map(employeeMapper::toResponseDto);
    }

    public List<EmployeeResponseDto> findAvailableEmployees(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return employeeRepository
                .findByAvailabilityDayOfWeekAndAvailabilityStartTimeBeforeAndAvailabilityEndTimeAfter(dayOfWeek, startTime, endTime)
                .stream()
                .map(employeeMapper::toResponseDto)
                .toList();
    }
}
