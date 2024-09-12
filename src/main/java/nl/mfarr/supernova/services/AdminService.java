package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.AdminRequestDto;
import nl.mfarr.supernova.dtos.AdminResponseDto;
import nl.mfarr.supernova.entities.AdminEntity;
import nl.mfarr.supernova.mappers.AdminMapper;
import nl.mfarr.supernova.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    public AdminResponseDto createAdmin(AdminRequestDto adminRequestDto) {
        if (adminRepository.existsByEmail(adminRequestDto.getEmail())) {
            throw new IllegalStateException("Email already in use.");
        }

        AdminEntity adminEntity = adminMapper.toEntity(adminRequestDto);
        adminEntity = adminRepository.save(adminEntity);
        return adminMapper.toResponseDto(adminEntity);
    }

    public Optional<AdminResponseDto> getAdminByEmail(String email) {
        return adminRepository.findByEmail(email)
                .map(adminMapper::toResponseDto);
    }
}
