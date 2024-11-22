package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.mappers.EmployeeMapper;
import nl.mfarr.supernova.models.EmployeeEntity;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeResponseDto> getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto);
    }

    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeDto) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeDto);
        EmployeeEntity savedEntity = employeeRepository.save(employeeEntity);
        return employeeMapper.toDto(savedEntity);
    }

    public EmployeeResponseDto updateEmployee(EmployeeRequestDto employeeDto) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeDto);
        EmployeeEntity updatedEntity = employeeRepository.save(employeeEntity);
        return employeeMapper.toDto(updatedEntity);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
