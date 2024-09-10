package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.AdminRequestDto;
import nl.mfarr.supernova.dtos.AdminResponseDto;
import nl.mfarr.supernova.entities.AdminEntity;

public class AdminMapper {

    public static AdminEntity toEntity(AdminRequestDto dto) {
        AdminEntity admin = new AdminEntity();
        admin.setEmail(dto.getEmail());
        admin.setPassword(dto.getPassword());
        return admin;
    }

    public static AdminResponseDto toResponseDto(AdminEntity admin) {
        AdminResponseDto dto = new AdminResponseDto();
        dto.setAdminId(admin.getAdminId());
        dto.setEmail(admin.getEmail());
        return dto;
    }
}
