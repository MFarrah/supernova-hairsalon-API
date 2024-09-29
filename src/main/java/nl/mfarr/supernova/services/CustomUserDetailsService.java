package nl.mfarr.supernova.services;

import jakarta.transaction.Transactional;
import nl.mfarr.supernova.dtos.PasswordChangeDto;
import nl.mfarr.supernova.entities.AdminEntity;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.enums.Gender;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.exceptions.*;
import nl.mfarr.supernova.helpers.PasswordEncoderHelper;
import nl.mfarr.supernova.repositories.AdminRepository;
import nl.mfarr.supernova.repositories.CustomerRepository;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Set;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoderHelper passwordEncoderHelper;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

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

    public String changePassword(PasswordChangeDto passwordChangeDto) {
        String email = passwordChangeDto.getEmail();
        String currentPassword = passwordChangeDto.getCurrentPassword();
        String newPassword = passwordChangeDto.getPassword();
        String confirmPassword = passwordChangeDto.getConfirmPassword();

        UserDetails userDetails = loadUserByUsername(email);
        if (!passwordEncoder.matches(currentPassword, userDetails.getPassword())) {
            throw new CurrentPasswordIncorrectException("Current password is incorrect");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new NoMatchingPasswordsException("New password and confirm password do not match");
        }

        String encodedNewPassword = passwordEncoderHelper.encode(newPassword);

        if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            AdminEntity admin = adminRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Admin not found"));
            admin.setPassword(encodedNewPassword);
            adminRepository.save(admin);
        } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            CustomerEntity customer = customerRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Customer not found"));
            customer.setPassword(encodedNewPassword);
            customerRepository.save(customer);
        } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))) {
            EmployeeEntity employee = employeeRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Employee not found"));
            employee.setPassword(encodedNewPassword);
            employeeRepository.save(employee);
        } else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE")) &&
                userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            EmployeeEntity employee = employeeRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Manager not found"));
            employee.setPassword(encodedNewPassword);
            employeeRepository.save(employee);
        } else {
            throw new RoleNotReconizedException("User role not recognized");
        }

        return "Password successfully changed";
    }
}