package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.services.EmployeeService;
import nl.mfarr.supernova.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
/*import org.springframework.security.access.prepost.PreAuthorize;*/
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<EmployeeResponseDto>> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDto employeeRequestDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

   /* @PreAuthorize("hasRole('ADMIN')")*/
    @PostMapping("/{id}/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        EmployeeResponseDto employee = employeeService.getEmployeeById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setPhotoFileName(fileName);
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(); // create a new request DTO and populate it
        employeeService.updateEmployee(employeeRequestDto);
        return ResponseEntity.ok("Photo uploaded successfully: " + fileName);
    }

    /*@PreAuthorize("hasRole('ADMIN')")*/
    @PostMapping("/{id}/uploadDocument")
    public ResponseEntity<String> uploadDocument(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        EmployeeResponseDto employee = employeeService.getEmployeeById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setDocumentFileName(fileName);
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(); // create a new request DTO and populate it
        employeeService.updateEmployee(employeeRequestDto);
        return ResponseEntity.ok("Document uploaded successfully: " + fileName);
    }
}
