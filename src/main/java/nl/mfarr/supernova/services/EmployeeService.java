package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.EmployeeCreateRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.exceptions.DuplicateEmailException;
import nl.mfarr.supernova.exceptions.EmailAlreadyRegisteredException;
import nl.mfarr.supernova.exceptions.EmailRequiredException;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.mappers.EmployeeMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmployeeResponseDto getEmployeeById(Long id) {
        EmployeeEntity entity = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        return employeeMapper.toDto(entity);
    }

    public EmployeeResponseDto createEmployee(EmployeeCreateRequestDto dto) {
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new EmailRequiredException("E-mail address required");
        }
        if (EmployeeRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyRegisteredException("This e-mail is already in use.");
        }
        EmployeeEntity entity = employeeMapper.toEntity(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(encodedPassword);
        entity.setRoles(Set.of(Role.EMPLOYEE));
        entity.setQualifiedOrderIds(dto.getQualifiedOrderIds());

        Set<ScheduleEntity> schedules = entity.getWorkingSchedule();
        if (schedules != null) {
            schedules = schedules.stream()
                    .map(scheduleRepository::save)
                    .collect(Collectors.toSet());
            entity.setWorkingSchedule(schedules);
        }

        try {
            entity = employeeRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Duplicate email: " + dto.getEmail() + ". Please try to log in or register with a different email adres.");
        }
        return employeeMapper.toDto(entity);
    }

    public EmployeeResponseDto createManager(EmployeeCreateRequestDto dto) {
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new EmailRequiredException("E-mail address required");
        }
        if (EmployeeRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyRegisteredException("This e-mail is already in use.");
        }
        EmployeeEntity entity = employeeMapper.toEntity(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(encodedPassword);
        entity.setRoles(Set.of(Role.EMPLOYEE, Role.ADMIN));

        Set<ScheduleEntity> schedules = entity.getWorkingSchedule();
        if (schedules != null) {
            schedules = schedules.stream()
                    .map(scheduleRepository::save)
                    .collect(Collectors.toSet());
            entity.setWorkingSchedule(schedules);
        }

        try {
            entity = employeeRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Duplicate email: " + dto.getEmail() + ". Please try to log in or register with a different email adres.");
        }
        return employeeMapper.toDto(entity);
    }

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public EmployeeEntity findById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
    }
}