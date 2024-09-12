package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.entities.CustomerEntity;

public class CustomerMapper {

    public static CustomerEntity toEntity(CustomerRequestDto dto) {
        CustomerEntity customer = new CustomerEntity();
        customer.setEmail(dto.getEmail());
        customer.setPassword(dto.getPassword());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setDateOfBirth(dto.getDateOfBirth());
        customer.setGender(dto.getGender());
        customer.setPhoneNumber(dto.getPhoneNumber());
        return customer;
    }

    public static CustomerResponseDto toResponseDto(CustomerEntity customer) {
        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setCustomerId(customer.getCustomerId());
        dto.setEmail(customer.getEmail());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        return dto;
    }
}
