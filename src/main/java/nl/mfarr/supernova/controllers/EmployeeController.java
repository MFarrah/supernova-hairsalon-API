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

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDto employeeResponse = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeCreateRequestDto employeeCreateRequestDto) {
        MatchingPasswordHelper.isMatching(employeeCreateRequestDto.getPassword(), employeeCreateRequestDto.getConfirmPassword());
        EmployeeResponseDto employeeResponse = employeeService.updateEmployee(id, employeeCreateRequestDto);
        return ResponseEntity.ok(employeeResponse);
    }


}