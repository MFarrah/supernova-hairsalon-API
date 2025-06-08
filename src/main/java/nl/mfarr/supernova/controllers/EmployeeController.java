package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.employeeDtos.EmployeeUpsertRequestDto;
import nl.mfarr.supernova.dtos.employeeDtos.EmployeeResponseDto;
import nl.mfarr.supernova.dtos.employeeDtos.EmployeeWithScheduleUpsertRequestDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.helpers.MatchingPasswordHelper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.security.CustomUserDetails;
import nl.mfarr.supernova.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final String uploadDir = "uploads/employees/";

    @PostMapping("/post")
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
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeUpsertRequestDto employeeUpsertRequestDto) {


        EmployeeResponseDto employeeResponse = employeeService.updateEmployee(
                id,
                employeeUpsertRequestDto
        );

        return ResponseEntity.ok(employeeResponse);
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            EmployeeEntity employeeEntity = employeeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filepath = Paths.get(uploadDir, filename);

            Files.createDirectories(filepath.getParent());
            Files.write(filepath, file.getBytes());

            String imageUrl = "/images/employees/" + filename;
            employeeEntity.setProfileImageUrl(imageUrl);
            employeeRepository.save(employeeEntity);

            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading file: " + e.getMessage());
        }
    }
}
