package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        EmployeeResponseDto employeeResponse = employeeService.createEmployee(employeeRequestDto);
        return ResponseEntity.ok(employeeResponse);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeByEmail(@PathVariable String email) {
        Optional<EmployeeResponseDto> employeeResponse = employeeService.getEmployeeByEmail(email);
        return employeeResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    public ResponseEntity<List<EmployeeResponseDto>> findAvailableEmployees(
            @RequestParam DayOfWeek dayOfWeek,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime) {
        List<EmployeeResponseDto> availableEmployees = employeeService.findAvailableEmployees(dayOfWeek, startTime, endTime);
        return ResponseEntity.ok(availableEmployees);
    }
}
