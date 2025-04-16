package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.employeeDtos.EmployeeUpsertRequestDto;
import nl.mfarr.supernova.dtos.employeeDtos.EmployeeResponseDto;
import nl.mfarr.supernova.dtos.employeeDtos.EmployeeWithScheduleUpsertRequestDto;
import nl.mfarr.supernova.helpers.MatchingPasswordHelper;
import nl.mfarr.supernova.security.CustomUserDetails;
import nl.mfarr.supernova.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/new-employee")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeUpsertRequestDto employeeUpsertRequestDto) {
        MatchingPasswordHelper.isMatching(employeeUpsertRequestDto.getPassword(), employeeUpsertRequestDto.getConfirmPassword());
        EmployeeResponseDto employeeResponse = employeeService.createEmployee(employeeUpsertRequestDto);
        return ResponseEntity.ok(employeeResponse);
    }


    @GetMapping("/all")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        List<EmployeeResponseDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !userId.equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        EmployeeResponseDto employeeResponse = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeResponse);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }


    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeWithScheduleUpsertRequestDto employeeWithScheduleDto) {
        // Controleer of het wachtwoord overeenkomt
        MatchingPasswordHelper.isMatching(employeeWithScheduleDto.getEmployee().getPassword(), employeeWithScheduleDto.getEmployee().getConfirmPassword());

        // Update de employee en schedules
        EmployeeResponseDto employeeResponse = employeeService.updateEmployee(id, employeeWithScheduleDto.getEmployee(), employeeWithScheduleDto.getSchedules());

        return ResponseEntity.ok(employeeResponse);
    }
}