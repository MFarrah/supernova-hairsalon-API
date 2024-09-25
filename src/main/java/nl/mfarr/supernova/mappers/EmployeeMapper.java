package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public static EmployeeEntity toEntity(EmployeeRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, EmployeeEntity.class);
    }

    public EmployeeResponseDto toDto(EmployeeEntity entity) {
        return GenericMapperHelper.mapToDto(entity, EmployeeResponseDto.class);
    }
}
