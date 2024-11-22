package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.AdminRequestDto;
import nl.mfarr.supernova.dtos.AdminResponseDto;
import nl.mfarr.supernova.entities.AdminEntity;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminEntity toEntity(AdminRequestDto dto) {
        AdminEntity admin = new AdminEntity();
        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(dto.getPassword());  // Mapping voor wachtwoord
        admin.setRoles(dto.getRoles());
        return admin;
    }

    public AdminResponseDto toResponseDto(AdminEntity admin) {
        AdminResponseDto response = new AdminResponseDto();
        response.setAdminId(admin.getAdminId());
        response.setFirstName(admin.getFirstName());
        response.setLastName(admin.getLastName());
        response.setEmail(admin.getEmail());
        response.setRoles(admin.getRoles());
        return response;
    }
}
