package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.enums.Gender;
import nl.mfarr.supernova.mappers.EmployeeMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeResponseDto createEmployee(EmployeeRequestDto dto) {
        EmployeeEntity entity = EmployeeMapper.toEntity(dto);
        EmployeeEntity savedEntity = employeeRepository.save(entity);
        return EmployeeMapper.toResponseDto(savedEntity);
    }

    public EmployeeResponseDto getEmployeeById(Long id) {
        EmployeeEntity entity = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        return EmployeeMapper.toResponseDto(entity);
    }

    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto dto) {
        EmployeeEntity entity = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setSkills(dto.getSkills());
        EmployeeEntity updatedEntity = employeeRepository.save(entity);
        return EmployeeMapper.toResponseDto(updatedEntity);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}