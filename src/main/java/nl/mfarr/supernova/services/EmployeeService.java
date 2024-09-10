package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.mappers.EmployeeMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
        EmployeeEntity employee = EmployeeMapper.toEntity(requestDto);
        EmployeeEntity savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toResponseDto(savedEmployee);
    }
}
