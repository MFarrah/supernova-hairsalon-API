package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.EmployeeCreateRequestDto;
import nl.mfarr.supernova.dtos.EmployeeUpdateRequestDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public static EmployeeEntity toEntity(EmployeeCreateRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, EmployeeEntity.class);
    }

    public static EmployeeEntity toEntity(EmployeeUpdateRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, EmployeeEntity.class);
    }

    public EmployeeResponseDto toDto(EmployeeEntity entity) {
        return GenericMapperHelper.mapToDto(entity, EmployeeResponseDto.class);
    }
}
