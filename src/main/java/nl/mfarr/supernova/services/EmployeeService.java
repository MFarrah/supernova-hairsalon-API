package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.EmployeeCreateRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.mappers.EmployeeMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public EmployeeResponseDto createEmployee(EmployeeCreateRequestDto employeeCreateRequestDto) {

        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeCreateRequestDto);

        String encodedPassword = passwordEncoder.encode(employeeCreateRequestDto.getPassword());
        employeeEntity.setPassword(encodedPassword);
        employeeEntity.setRoles(Set.of(Role.EMPLOYEE));

        employeeEntity = employeeRepository.save(employeeEntity);

        return employeeMapper.toDto(employeeEntity);
    }
    public EmployeeResponseDto createManager(EmployeeCreateRequestDto employeeCreateRequestDto) {

        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeCreateRequestDto);

        String encodedPassword = passwordEncoder.encode(employeeCreateRequestDto.getPassword());
        employeeEntity.setPassword(encodedPassword);
        employeeEntity.setRoles(Set.of(Role.EMPLOYEE, Role.ADMIN));

        employeeEntity = employeeRepository.save(employeeEntity);

        return employeeMapper.toDto(employeeEntity);
    }

public Iterable<EmployeeResponseDto> getAllEmployees() {
    return employeeRepository.findAll()
                             .stream()
                             .map(employeeMapper::toDto)
                             .collect(Collectors.toList());
}
}



