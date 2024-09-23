package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Endpoint om een nieuwe medewerker aan te maken (alleen admin)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        EmployeeResponseDto employee = employeeService.createEmployee(employeeRequestDto);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    // Endpoint om alle medewerkers op te halen (alleen admin)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        List<EmployeeResponseDto> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Endpoint om een medewerker op te halen op basis van ID (toegankelijk voor admin en medewerkers zelf)
    @GetMapping("/{employeeId}")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long employeeId) {
        EmployeeResponseDto employee = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // Endpoint om de kwalificaties van een medewerker bij te werken (alleen admin)
    @PutMapping("/{employeeId}/qualifications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmployeeResponseDto> updateEmployeeQualifications(@PathVariable Long employeeId,
                                                                            @RequestBody Set<Long> qualifiedOrderIds) {
        EmployeeResponseDto employee = employeeService.updateEmployeeQualifications(employeeId, qualifiedOrderIds);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // Endpoint om een medewerker te verwijderen (alleen admin)
    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
