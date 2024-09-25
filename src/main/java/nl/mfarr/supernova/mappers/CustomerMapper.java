package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public static CustomerEntity toEntity(CustomerRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, CustomerEntity.class);
    }

    public CustomerResponseDto toDto(CustomerEntity entity) {
        return GenericMapperHelper.mapToDto(entity, CustomerResponseDto.class);
    }
}
