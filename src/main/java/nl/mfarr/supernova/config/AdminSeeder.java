package nl.mfarr.supernova.config;

import nl.mfarr.supernova.entities.AdminEntity;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.repositories.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AdminSeeder {

    @Bean
    public CommandLineRunner seedAdmin(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (adminRepository.findByEmail("admin@supernova.api").isEmpty()) {
                AdminEntity admin = new AdminEntity();
                admin.setEmail("admin@supernova.api");
                admin.setPassword(passwordEncoder.encode("0000"));
                admin.setRoles(Set.of(Role.ADMIN));
                adminRepository.save(admin);
            }
        };
    }
}
