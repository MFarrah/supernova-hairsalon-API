package nl.mfarr.supernova.services;

import nl.mfarr.supernova.entities.AdminEntity;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.exceptions.EmailAlreadyRegisteredException;
import nl.mfarr.supernova.exceptions.EmailRequiredException;
import nl.mfarr.supernova.repositories.AdminRepository;
import nl.mfarr.supernova.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AdminEntity admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.withUsername(admin.getEmail())
                .password(admin.getPassword())
                .roles(admin.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }

    public UserDetails registerCustomer(String email, String password) {
        // Controleer of het e-mailadres is ingevuld
        if (email == null || email.isEmpty()) {
            throw new EmailRequiredException("E-mail adres required");
        }

        // Controleer of het e-mailadres al bestaat
        if (customerRepository.existsByEmail(email)) {
            throw new EmailAlreadyRegisteredException("This e-mail is already in use.");
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setRoles(Set.of(Role.CUSTOMER));
        customerRepository.save(customer);

        return org.springframework.security.core.userdetails.User.withUsername(customer.getEmail())
                .password(customer.getPassword())
                .roles(customer.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }
}