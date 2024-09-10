package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.AdminRequestDto;
import nl.mfarr.supernova.dtos.AdminResponseDto;
import nl.mfarr.supernova.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    public AdminResponseDto createAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        return adminService.createAdmin(adminRequestDto);
    }

    @GetMapping("/{id}")
    public AdminResponseDto getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    @GetMapping
    public List<AdminResponseDto> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @PutMapping("/{id}")
    public AdminResponseDto updateAdmin(@PathVariable Long id, @RequestBody AdminRequestDto adminRequestDto) {
        return adminService.updateAdmin(id, adminRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
    }
}