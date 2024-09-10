package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.AdminRequestDto;
import nl.mfarr.supernova.dtos.AdminResponseDto;
import nl.mfarr.supernova.entities.AdminEntity;
import nl.mfarr.supernova.mappers.AdminMapper;
import nl.mfarr.supernova.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public AdminResponseDto createAdmin(AdminRequestDto dto) {
        AdminEntity entity = AdminMapper.toEntity(dto);
        AdminEntity savedEntity = adminRepository.save(entity);
        return AdminMapper.toResponseDto(savedEntity);
    }

    public AdminResponseDto getAdminById(Long id) {
        AdminEntity entity = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
        return AdminMapper.toResponseDto(entity);
    }

    public List<AdminResponseDto> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(AdminMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public AdminResponseDto updateAdmin(Long id, AdminRequestDto dto) {
        AdminEntity entity = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin not found"));
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        AdminEntity updatedEntity = adminRepository.save(entity);
        return AdminMapper.toResponseDto(updatedEntity);
    }

    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}