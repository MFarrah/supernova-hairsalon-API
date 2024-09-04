package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.models.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponseDto toDto(CustomerEntity customerEntity) {
        CustomerResponseDto customerDto = new CustomerResponseDto();
        customerDto.setCustomerId(customerEntity.getCustomerId());
        customerDto.setName(customerEntity.getName());
        customerDto.setEmail(customerEntity.getEmail());
        customerDto.setPhoneNumber(customerEntity.getPhoneNumber());
        return customerDto;
    }

    public CustomerEntity toEntity(CustomerRequestDto customerDto) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customerDto.getName());
        customerEntity.setEmail(customerDto.getEmail());
        customerEntity.setPhoneNumber(customerDto.getPhoneNumber());
        customerEntity.setPassword(customerDto.getPassword());
        return customerEntity;
    }
}
