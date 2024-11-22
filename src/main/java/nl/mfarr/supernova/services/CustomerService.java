package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.mappers.CustomerMapper;
import nl.mfarr.supernova.models.CustomerEntity;
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

    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CustomerResponseDto> getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDto);
    }

    public CustomerResponseDto createCustomer(CustomerRequestDto customerDto) {
        CustomerEntity customerEntity = customerMapper.toEntity(customerDto);
        CustomerEntity savedEntity = customerRepository.save(customerEntity);
        return customerMapper.toDto(savedEntity);
    }

    public CustomerResponseDto updateCustomer(CustomerRequestDto customerDto) {
        CustomerEntity customerEntity = customerMapper.toEntity(customerDto);
        CustomerEntity updatedEntity = customerRepository.save(customerEntity);
        return customerMapper.toDto(updatedEntity);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
