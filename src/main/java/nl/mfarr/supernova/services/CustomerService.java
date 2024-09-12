package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.mappers.CustomerMapper;
import nl.mfarr.supernova.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerResponseDto registerCustomer(CustomerRequestDto requestDto) {
        CustomerEntity customer = CustomerMapper.toEntity(requestDto);
        customer.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        CustomerEntity savedCustomer = customerRepository.save(customer);
        return CustomerMapper.toResponseDto(savedCustomer);
    }
}
