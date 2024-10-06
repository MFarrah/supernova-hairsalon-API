package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.entities.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerMapper() {
    }

    public CustomerEntity toEntity(CustomerRequestDto dto) {
        if (dto == null) {
            return null;
        }
        CustomerEntity entity = new CustomerEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setPassword(dto.getPassword());
        entity.setGender(dto.getGender());
        entity.setRoles(dto.getRoles());
        return entity;
    }

    public CustomerResponseDto toDto(CustomerEntity entity) {
        if (entity == null) {
            return null;
        }
        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setGender(entity.getGender());
        dto.setRoles(entity.getRoles());
        return dto;
    }
}