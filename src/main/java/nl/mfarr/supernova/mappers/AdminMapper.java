package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.AdminRequestDto;
import nl.mfarr.supernova.dtos.AdminResponseDto;
import nl.mfarr.supernova.entities.AdminEntity;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminEntity toEntity(AdminRequestDto adminRequestDto) {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setEmail(adminRequestDto.getEmail());
        adminEntity.setPassword(adminRequestDto.getPassword());
        return adminEntity;
    }

    public AdminResponseDto toResponseDto(AdminEntity adminEntity) {
        AdminResponseDto responseDto = new AdminResponseDto();
        responseDto.setAdminId(adminEntity.getAdminId());
        responseDto.setEmail(adminEntity.getEmail());
        responseDto.setRole(adminEntity.getRole());
        return responseDto;
    }
}
