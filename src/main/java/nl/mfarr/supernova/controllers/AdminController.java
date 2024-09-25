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
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;



    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminResponseDto> updateAdmin(@PathVariable String email, @RequestBody AdminRequestDto adminRequestDto) {
        AdminResponseDto adminResponse = adminService.updateAdmin(email, adminRequestDto);
        return ResponseEntity.ok(adminResponse);
    }

}
