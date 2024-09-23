package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.mappers.EmployeeMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
        EmployeeEntity employee = employeeMapper.toEntity(requestDto);

        // Map qualified orders
        Set<OrderEntity> qualifiedOrders = requestDto.getQualifiedOrderIds().stream()
                .map(orderRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        employee.setQualifiedOrders(qualifiedOrders);
        employeeRepository.save(employee);
        return employeeMapper.toResponseDto(employee);
    }

    public EmployeeResponseDto getEmployeeById(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Medewerker niet gevonden"));
        return employeeMapper.toResponseDto(employee);
    }

    // Haal alle medewerkers op
    public List<EmployeeResponseDto> getAllEmployees() {
        List<EmployeeEntity> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employeeMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    // Werk kwalificaties van een medewerker bij
    public EmployeeResponseDto updateEmployeeQualifications(Long employeeId, Set<Long> qualifiedOrderIds) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Medewerker niet gevonden"));

        Set<OrderEntity> qualifiedOrders = qualifiedOrderIds.stream()
                .map(orderRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        employee.setQualifiedOrders(qualifiedOrders);
        employeeRepository.save(employee);
        return employeeMapper.toResponseDto(employee);
    }

    // Verwijder een medewerker
    public void deleteEmployee(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Medewerker niet gevonden"));
        employeeRepository.delete(employee);
    }

    // Add other business logic if needed
}