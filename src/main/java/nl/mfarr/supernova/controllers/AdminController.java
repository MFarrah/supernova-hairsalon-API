package nl.mfarr.supernova.controllers;
import nl.mfarr.supernova.services.AdminService;
import nl.mfarr.supernova.dtos.adminDtos.AdminResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admins")

public class AdminController {

    @Autowired
    private AdminService adminService;

    // add search by id
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDto> getAdminById(@PathVariable Long id) {
        AdminResponseDto adminResponse = adminService.getAdminById(id);
        return ResponseEntity.ok(adminResponse);
    }
}
