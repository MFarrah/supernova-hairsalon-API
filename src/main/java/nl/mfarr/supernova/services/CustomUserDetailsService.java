package nl.mfarr.supernova.services;

import jakarta.transaction.Transactional;
import nl.mfarr.supernova.entities.AdminEntity;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.enums.Gender;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.exceptions.EmailAlreadyRegisteredException;
import nl.mfarr.supernova.exceptions.EmailRequiredException;
import nl.mfarr.supernova.helpers.PasswordEncoderHelper;
import nl.mfarr.supernova.repositories.AdminRepository;
import nl.mfarr.supernova.repositories.CustomerRepository;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoderHelper passwordEncoderHelper;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminEntity admin = adminRepository.findByEmail(email).orElse(null);
        if (admin != null) {
            return org.springframework.security.core.userdetails.User.withUsername(admin.getEmail())
                    .password(admin.getPassword())
                    .roles(admin.getRoles().stream().map(Enum::name).toArray(String[]::new))
                    .build();
        }

        CustomerEntity customer = customerRepository.findByEmail(email).orElse(null);
        if (customer != null) {
            return org.springframework.security.core.userdetails.User.withUsername(customer.getEmail())
                    .password(customer.getPassword())
                    .roles(customer.getRoles().stream().map(Enum::name).toArray(String[]::new))
                    .build();
        }

        EmployeeEntity employee = employeeRepository.findByEmail(email).orElse(null);
        if (employee != null) {
            return org.springframework.security.core.userdetails.User.withUsername(employee.getEmail())
                    .password(employee.getPassword())
                    .roles(employee.getRoles().stream().map(Enum::name).toArray(String[]::new))
                    .build();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
    public UserDetails registerCustomer(String email, String password, String firstName, String lastName, String phoneNumber, Gender gender, LocalDate dateOfBirth) {
        // Controleer of het e-mailadres is ingevuld
        if (email == null || email.isEmpty()) {
            throw new EmailRequiredException("E-mail adres required");
        }

        // Controleer of het e-mailadres al bestaat
        if (customerRepository.existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException("This e-mail is already in use.");
        }
        String encodedPassword = passwordEncoderHelper.encode(password);
        CustomerEntity customer = new CustomerEntity();
        customer.setEmail(email);
        customer.setPassword(encodedPassword);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhoneNumber(phoneNumber);
        customer.setDateOfBirth(dateOfBirth);
        customer.setGender(gender);
        customer.setRoles(Set.of(Role.CUSTOMER));
        customerRepository.save(customer);

        return org.springframework.security.core.userdetails.User.withUsername(customer.getEmail())
                .password(customer.getPassword())
                .roles(customer.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }
}