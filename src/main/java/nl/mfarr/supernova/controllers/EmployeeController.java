package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.EmployeeUpsertRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.helpers.MatchingPasswordHelper;
import nl.mfarr.supernova.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new-employee")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeUpsertRequestDto employeeUpsertRequestDto) {
        MatchingPasswordHelper.isMatching(employeeUpsertRequestDto.getPassword(), employeeUpsertRequestDto.getConfirmPassword());
        EmployeeResponseDto employeeResponse = employeeService.createEmployee(employeeUpsertRequestDto);
        return ResponseEntity.ok(employeeResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new-manager")
    public ResponseEntity<EmployeeResponseDto> createManager(@RequestBody EmployeeUpsertRequestDto employeeUpsertRequestDto) {
        MatchingPasswordHelper.isMatching(employeeUpsertRequestDto.getPassword(), employeeUpsertRequestDto.getConfirmPassword());
        EmployeeResponseDto employeeResponse = employeeService.createManager(employeeUpsertRequestDto);
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
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> patchEmployee(@PathVariable Long id, @RequestBody EmployeeUpsertRequestDto employeeUpsertRequestDto) {
        MatchingPasswordHelper.isMatching(employeeUpsertRequestDto.getPassword(), employeeUpsertRequestDto.getConfirmPassword());
        EmployeeResponseDto employeeResponse = employeeService.patchEmployee(id, employeeUpsertRequestDto);
        return ResponseEntity.ok(employeeResponse);
    }


}