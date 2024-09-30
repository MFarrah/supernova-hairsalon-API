package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.*;
import nl.mfarr.supernova.entities.*;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.mappers.EmployeeMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmployeeResponseDto createEmployee(EmployeeCreateRequestDto dto) {
        EmployeeEntity entity = employeeMapper.toEntity(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(encodedPassword);
        entity.setRoles(Set.of(Role.EMPLOYEE));

        // Save schedules
        Set<ScheduleEntity> schedules = entity.getWorkingSchedule();
        if (schedules != null) {
            schedules = schedules.stream()
                    .map(scheduleRepository::save)
                    .collect(Collectors.toSet());
            entity.setWorkingSchedule(schedules);
        }

        entity = employeeRepository.save(entity);
        return employeeMapper.toDto(entity);
    }

    public EmployeeResponseDto createManager(EmployeeCreateRequestDto dto) {
        EmployeeEntity entity = employeeMapper.toEntity(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(encodedPassword);
        entity.setRoles(Set.of(Role.EMPLOYEE, Role.ADMIN));

        // Save schedules
        Set<ScheduleEntity> schedules = entity.getWorkingSchedule();
        if (schedules != null) {
            schedules = schedules.stream()
                    .map(scheduleRepository::save)
                    .collect(Collectors.toSet());
            entity.setWorkingSchedule(schedules);
        }

        entity = employeeRepository.save(entity);
        return employeeMapper.toDto(entity);
    }

    public Iterable<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }
}