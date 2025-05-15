package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.employeeDtos.EmployeeUpsertRequestDto;
import nl.mfarr.supernova.dtos.employeeDtos.EmployeeResponseDto;
import nl.mfarr.supernova.dtos.scheduleDtos.ScheduleUpsertRequestDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.exceptions.EmployeeExistsByEmailException;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.mappers.EmployeeMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String uploadProfileImage(Long employeeId, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty");
        }

        String uploadDir = "uploads/employees/" + employeeId;
        java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);

        if (!java.nio.file.Files.exists(uploadPath)) {
            java.nio.file.Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        java.nio.file.Path filePath = uploadPath.resolve(fileName);
        file.transferTo(filePath.toFile());

        return filePath.toString();
    }

    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeUpsertRequestDto requestDto) {
        if (employeeRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new EmployeeExistsByEmailException("Employee already exists with email: " + requestDto.getEmail());
        }

        EmployeeEntity employee = employeeMapper.toEntity(requestDto);
        employee.setRoles(Collections.singleton(Role.EMPLOYEE));
        employee.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        Set<ScheduleEntity> workingSchedule = new HashSet<>();
        for (ScheduleEntity schedule : employee.getWorkingSchedule()) {
            schedule.setEmployee(employee);
            workingSchedule.add(schedule);
        }

        employee.setWorkingSchedule(workingSchedule);
        EmployeeEntity savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toDto(savedEmployee);
    }

    @Transactional
    public EmployeeResponseDto updateEmployee(Long employeeId, EmployeeUpsertRequestDto requestDto, Set<ScheduleUpsertRequestDto> schedules) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeMapper.updateEntityFromDto(requestDto, existingEmployee);

        Set<ScheduleEntity> workingSchedule = new HashSet<>();
        for (ScheduleEntity schedule : existingEmployee.getWorkingSchedule()) {
            schedule.setEmployee(existingEmployee);
            workingSchedule.add(schedule);
        }

        existingEmployee.setWorkingSchedule(workingSchedule);
        EmployeeEntity updatedEmployee = employeeRepository.save(existingEmployee);

        return employeeMapper.toDto(updatedEmployee);
    }

    public List<EmployeeResponseDto> getAllEmployees() {
        List<EmployeeEntity> employees = employeeRepository.findAll();
        return employeeMapper.toDtoList(employees);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    public EmployeeResponseDto getEmployeeById(Long id) {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        return employeeMapper.toDto(employee);
    }
}