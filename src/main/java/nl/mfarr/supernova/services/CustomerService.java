package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.mappers.CustomerMapper;
import nl.mfarr.supernova.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        if (customerRepository.existsByEmail(customerRequestDto.getEmail())) {
            throw new IllegalStateException("Email already in use.");
        }

        CustomerEntity customerEntity = customerMapper.toEntity(customerRequestDto);
        customerEntity = customerRepository.save(customerEntity);
        return customerMapper.toResponseDto(customerEntity);
    }

    public Optional<CustomerResponseDto> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerMapper::toResponseDto);
    }

    public Optional<CustomerResponseDto> getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .map(customerMapper::toResponseDto);
    }

    public Optional<CustomerResponseDto> getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toResponseDto);
    }
}
