package nl.mfarr.supernova.services;

import jakarta.persistence.EntityNotFoundException;
import nl.mfarr.supernova.dtos.adminDtos.AdminResponseDto;
import nl.mfarr.supernova.mappers.AdminMapper;
import nl.mfarr.supernova.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;


    public AdminResponseDto getAdminById(Long id) {
        return adminRepository.findById(id)
                .map(adminMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));
    }


}
