package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.AdminRequestDto;
import nl.mfarr.supernova.dtos.AdminResponseDto;
import nl.mfarr.supernova.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AdminResponseDto> createAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        AdminResponseDto adminResponse = adminService.createAdmin(adminRequestDto);
        return ResponseEntity.ok(adminResponse);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{email}")
    public ResponseEntity<AdminResponseDto> getAdminByEmail(@PathVariable String email) {
        Optional<AdminResponseDto> adminResponse = adminService.getAdminByEmail(email);
        return adminResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
