package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.customerDtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.customerDtos.CustomerResponseDto;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.mappers.CustomerMapper;
import nl.mfarr.supernova.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        String password = customerRequestDto.getPassword();
        String confirmPassword = customerRequestDto.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            throw new IllegalStateException("Passwords do not match.");
        }
        CustomerEntity customerEntity = customerMapper.toEntity(customerRequestDto);
        customerEntity = customerRepository.save(customerEntity);
        return customerMapper.toDto(customerEntity);
    }

    public Optional<CustomerResponseDto> getCustomerByEmail(String email) {
        Optional<CustomerEntity> customers = customerRepository.findByEmail(email);
        if (customers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(customerMapper.toDto(customers.get()));
    }

    public Optional<CustomerResponseDto> getCustomerByPhoneNumber(String phoneNumber) {
        Optional<CustomerEntity> customers = customerRepository.findByPhoneNumber(phoneNumber);
        if (customers.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(customerMapper.toDto(customers.get()));
    }

    public Optional<CustomerResponseDto> getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDto);
    }

    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }
}