package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.enums.Gender;

import java.util.stream.Collectors;

public class CustomerMapper {

    public static CustomerEntity toEntity(CustomerRequestDto dto) {
        CustomerEntity entity = new CustomerEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }

    public static CustomerResponseDto toResponseDto(CustomerEntity entity) {
        return new CustomerResponseDto(entity);
    }
}