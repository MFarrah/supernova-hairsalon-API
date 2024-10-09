package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.EmployeeCreateRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.helpers.MatchingPasswordHelper;
import nl.mfarr.supernova.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new-employee")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeCreateRequestDto employeeCreateRequestDto) {
        MatchingPasswordHelper.isMatching(employeeCreateRequestDto.getPassword(), employeeCreateRequestDto.getConfirmPassword());
        EmployeeResponseDto employeeResponse = employeeService.createEmployee(employeeCreateRequestDto);
        return ResponseEntity.ok(employeeResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new-manager")
    public ResponseEntity<EmployeeResponseDto> createManager(@RequestBody EmployeeCreateRequestDto employeeCreateRequestDto) {
        MatchingPasswordHelper.isMatching(employeeCreateRequestDto.getPassword(), employeeCreateRequestDto.getConfirmPassword());
        EmployeeResponseDto employeeResponse = employeeService.createManager(employeeCreateRequestDto);
        return ResponseEntity.ok(employeeResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        List<EmployeeResponseDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/profile")
    public Optional<EmployeeResponseDto> getCustomerDetails(Authentication authentication) {

        return employeeService.getEmployeeByEmail(authentication.getName());
    }


}