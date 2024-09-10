package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.enums.Gender;
import nl.mfarr.supernova.mappers.CustomerMapper;
import nl.mfarr.supernova.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerResponseDto createCustomer(CustomerRequestDto dto) {
        CustomerEntity entity = CustomerMapper.toEntity(dto);
        CustomerEntity savedEntity = customerRepository.save(entity);
        return CustomerMapper.toResponseDto(savedEntity);
    }

    public CustomerResponseDto getCustomerById(Long id) {
        CustomerEntity entity = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        return CustomerMapper.toResponseDto(entity);
    }

    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public CustomerResponseDto updateCustomer(Long id, CustomerRequestDto dto) {
        CustomerEntity entity = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        entity.setPhoneNumber(dto.getPhoneNumber());
        CustomerEntity updatedEntity = customerRepository.save(entity);
        return CustomerMapper.toResponseDto(updatedEntity);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}