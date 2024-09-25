package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.entities.AdminEntity;
import nl.mfarr.supernova.dtos.AdminRequestDto;
import nl.mfarr.supernova.dtos.AdminResponseDto;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public static AdminEntity toEntity(AdminRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, AdminEntity.class);
    }

    public static AdminResponseDto toDto(AdminEntity entity) {
        return GenericMapperHelper.mapToDto(entity, AdminResponseDto.class);
    }
}
