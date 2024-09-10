package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.AdminRequestDto;
import nl.mfarr.supernova.dtos.AdminResponseDto;
import nl.mfarr.supernova.entities.AdminEntity;

public class AdminMapper {

    public static AdminEntity toEntity(AdminRequestDto dto) {
        AdminEntity entity = new AdminEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    public static AdminResponseDto toResponseDto(AdminEntity entity) {
        AdminResponseDto dto = new AdminResponseDto();
        dto.setAdminId(entity.getAdminId());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole().name());
        return dto;
    }
}