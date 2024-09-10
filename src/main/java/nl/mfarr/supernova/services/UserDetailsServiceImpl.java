package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.entities.AdminEntity;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.repositories.AdminRepository;
import nl.mfarr.supernova.repositories.CustomerRepository;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminEntity admin = adminRepository.findByEmail(email).orElse(null);
        if (admin != null) {
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();
        }

        EmployeeEntity employee = employeeRepository.findByEmail(email).orElse(null);
        if (employee != null) {
            return User.builder()
                    .username(employee.getEmail())
                    .password(employee.getPassword())
                    .roles("EMPLOYEE")
                    .build();
        }

        CustomerEntity customer = customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .roles("CUSTOMER")
                .build();
    }

    public CustomerResponseDto registerCustomer(CustomerRequestDto customerRequestDto) {
        CustomerEntity customer = new CustomerEntity();
        customer.setEmail(customerRequestDto.getEmail());
        customer.setPassword(customerRequestDto.getPassword());
        customerRepository.save(customer);
        customer.setEmail(customerRequestDto.getEmail());
        return new CustomerResponseDto(customer);
    }
}