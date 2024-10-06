package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.PasswordChangeDto;
import nl.mfarr.supernova.entities.AdminEntity;
import nl.mfarr.supernova.dtos.AdminRequestDto;
import nl.mfarr.supernova.dtos.AdminResponseDto;
import nl.mfarr.supernova.helpers.PasswordEncoderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    private final PasswordEncoderHelper passwordEncoderHelper;

    @Autowired
    public AdminMapper(PasswordEncoderHelper passwordEncoderHelper) {
        this.passwordEncoderHelper = passwordEncoderHelper;
    }

    public AdminEntity toEntity(AdminRequestDto dto) {
        if (dto == null) {
            return null;
        }
        AdminEntity entity = new AdminEntity();
        entity.setPassword(passwordEncoderHelper.encode(dto.getPassword()));
        entity.setEmail(dto.getEmail());
        return entity;
    }

    public AdminEntity toEntity(PasswordChangeDto dto) {
        if (dto == null) {
            return null;
        }
        AdminEntity entity = new AdminEntity();
        entity.setPassword(passwordEncoderHelper.encode(dto.getPassword()));
        return entity;
    }

    public AdminResponseDto toDto(AdminEntity entity) {
        if (entity == null) {
            return null;
        }
        AdminResponseDto dto = new AdminResponseDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}