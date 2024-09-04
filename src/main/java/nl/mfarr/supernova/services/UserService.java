package nl.mfarr.supernova.services;

import nl.mfarr.supernova.models.UserEntity;
import nl.mfarr.supernova.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity registerNewUser(String username, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        // Voeg standaard rol toe, bijvoorbeeld "ROLE_USER"
        user.setRoles(Set.of("ROLE_USER"));
        return userRepository.save(user);
    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
