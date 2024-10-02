package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.EmployeeCreateRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.exceptions.HasNoWorkingScheduleException;
import nl.mfarr.supernova.exceptions.NoRosterFoundException;
import nl.mfarr.supernova.exceptions.RosterExistsException;
import nl.mfarr.supernova.mappers.EmployeeMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
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

    @Autowired
    private RosterService rosterService;

    @Autowired
    private RosterRepository rosterRepository;

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

    public void generateAndSaveRosterForEmployee(Long id) {
        // Get employee
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        if (employee.getWorkingSchedule().isEmpty()) {
            throw new HasNoWorkingScheduleException("Employee has no working schedule");
        }
        if (rosterRepository.existsByEmployeeAndDate(employee, LocalDate.now())) {
            throw new RosterExistsException("Roster already exists for employee");
        }

        // Generate and save roster
        List<RosterEntity> monthlyRoster = rosterService.generateMonthlyRoster(employee.getEmployeeId());
        rosterRepository.saveAll(monthlyRoster);
    }

    public String getRosterForEmployee(Long id) {
        // Get employee
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        log.info("Fetching roster for employee with id: {}", id);
        // Get roster
        List<RosterEntity> roster = rosterRepository.findByEmployee(employee);
        if (roster.isEmpty()) {
            throw new NoRosterFoundException("No roster found for employee");
        }
        return roster.stream()
                .map(RosterEntity::toString)
                .collect(Collectors.joining(", "));
    }
}