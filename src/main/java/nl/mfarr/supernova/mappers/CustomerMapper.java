package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.entities.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerEntity toEntity(CustomerRequestDto customerRequestDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setEmail(customerRequestDto.getEmail());
        customerEntity.setPassword(customerRequestDto.getPassword());
        customerEntity.setFirstName(customerRequestDto.getFirstName());
        customerEntity.setLastName(customerRequestDto.getLastName());
        customerEntity.setDateOfBirth(customerRequestDto.getDateOfBirth());
        customerEntity.setGender(customerRequestDto.getGender());
        customerEntity.setPhoneNumber(customerRequestDto.getPhoneNumber());
        return customerEntity;
    }

    public CustomerResponseDto toResponseDto(CustomerEntity customerEntity) {
        CustomerResponseDto responseDto = new CustomerResponseDto();
        responseDto.setCustomerId(customerEntity.getCustomerId());
        responseDto.setEmail(customerEntity.getEmail());
        responseDto.setFirstName(customerEntity.getFirstName());
        responseDto.setLastName(customerEntity.getLastName());
        responseDto.setGender(customerEntity.getGender());
        responseDto.setRoles(customerEntity.getRoles());
        return responseDto;
    }
}
