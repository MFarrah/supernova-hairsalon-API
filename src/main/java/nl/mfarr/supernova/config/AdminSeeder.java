package nl.mfarr.supernova.config;

import nl.mfarr.supernova.entities.UserEntity;
import nl.mfarr.supernova.enums.Role;
import nl.mfarr.supernova.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class AdminSeeder {

    @Bean
    public CommandLineRunner seedAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                UserEntity admin = new UserEntity();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("0000"));
                admin.setRoles(Set.of(Role.ADMIN));
                userRepository.save(admin);
            }
        };
    }
}
