package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.UserRequestDto;
import nl.mfarr.supernova.dtos.UserResponseDto;
import nl.mfarr.supernova.models.UserEntity;
import nl.mfarr.supernova.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpassword"));
            Set<String> roles = new HashSet<>();
            roles.add("ROLE_ADMIN");
            admin.setRoles(roles);
            userRepository.save(admin);
        }
    }

    public Optional<UserResponseDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserResponseDto(user.getUsername()));
    }

    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        UserEntity user = new UserEntity();
        user.setUsername(userRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);
        UserEntity savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser.getUsername());
    }

    public UserResponseDto registerNewUser(String username, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);
        UserEntity savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser.getUsername());
    }
}