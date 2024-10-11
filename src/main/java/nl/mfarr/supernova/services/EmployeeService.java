package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.EmployeeUpsertRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.dtos.ScheduleUpsertRequestDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.mappers.EmployeeMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeUpsertRequestDto requestDto) {
        // Map the DTO to EmployeeEntity

        EmployeeEntity employee = employeeMapper.toEntity(requestDto);
        employee.setRoles(Collections.singleton(Role.EMPLOYEE));
        //encode password
        employee.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        // Process the working schedule and set employee for each schedule
        Set<ScheduleEntity> workingSchedule = new HashSet<>();
        for (ScheduleEntity schedule : employee.getWorkingSchedule()) {
            schedule.setEmployee(employee);  // Set the employee reference in each schedule
            workingSchedule.add(schedule);
        }

        // Save employee and working schedules (cascades should handle schedule saving)
        employee.setWorkingSchedule(workingSchedule);
        EmployeeEntity savedEmployee = employeeRepository.save(employee);

        // Return the mapped EmployeeResponseDto
        return employeeMapper.toDto(savedEmployee);
    }

    @Transactional
    public EmployeeResponseDto updateEmployee(Long employeeId, EmployeeUpsertRequestDto requestDto, Set<ScheduleUpsertRequestDto> schedules) {
        // Retrieve the existing employee
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Map the DTO to EmployeeEntity and update fields
        employeeMapper.updateEntityFromDto(requestDto, existingEmployee);

        // Process the working schedule and set employee for each schedule
        Set<ScheduleEntity> workingSchedule = new HashSet<>();
        for (ScheduleEntity schedule : existingEmployee.getWorkingSchedule()) {
            schedule.setEmployee(existingEmployee);  // Set the employee reference in each schedule
            workingSchedule.add(schedule);
        }

        // Save the updated employee and schedules
        existingEmployee.setWorkingSchedule(workingSchedule);
        EmployeeEntity updatedEmployee = employeeRepository.save(existingEmployee);

        // Return the updated employee as a DTO
        return employeeMapper.toDto(updatedEmployee);
    }

    public List<EmployeeResponseDto> getAllEmployees() {
        List<EmployeeEntity> employees = employeeRepository.findAll();
        return Collections.singletonList(employeeMapper.toDto((EmployeeEntity) employees));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeResponseDto getEmployeeById(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        return employeeMapper.toDto(employee);
    }

    // Additional methods for retrieving, deleting employees can be added as needed
}
