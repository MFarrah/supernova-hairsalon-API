package nl.mfarr.supernova.services;

import nl.mfarr.supernova.entities.AdminEntity;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.repositories.AdminRepository;
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
        AdminEntity admin = new AdminEntity();
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRoles(Set.of(Role.CUSTOMER));
        adminRepository.save(admin);

        return org.springframework.security.core.userdetails.User.withUsername(admin.getEmail())
                .password(admin.getPassword())
                .roles(admin.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }
}