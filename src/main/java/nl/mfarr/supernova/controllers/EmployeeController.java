package nl.mfarr.supernova.controllers;


import nl.mfarr.supernova.dtos.EmployeeCreateRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.helpers.MatchingPasswordHelper;
import nl.mfarr.supernova.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeCreateRequestDto employeeCreateRequestDto) {
        // The MatchingPasswordHelper already handles the bad request if passwords don't match
        MatchingPasswordHelper.isMatching(employeeCreateRequestDto.getPassword(), employeeCreateRequestDto.getConfirmPassword());

        // If the passwords match, proceed with creating the employee
        EmployeeResponseDto employeeResponse = employeeService.createEmployee(employeeCreateRequestDto);
        return ResponseEntity.ok(employeeResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-manager")
    public ResponseEntity<EmployeeResponseDto> createManager(@RequestBody EmployeeCreateRequestDto employeeCreateRequestDto) {

        MatchingPasswordHelper.isMatching(employeeCreateRequestDto.getPassword(), employeeCreateRequestDto.getConfirmPassword());

        EmployeeResponseDto employeeResponse = employeeService.createManager(employeeCreateRequestDto);
        return ResponseEntity.ok(employeeResponse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Iterable<EmployeeResponseDto>> getAllEmployees() {
        Iterable<EmployeeResponseDto> employeeResponse = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeResponse);
    }


}

