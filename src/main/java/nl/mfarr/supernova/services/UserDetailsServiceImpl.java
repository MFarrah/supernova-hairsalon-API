// UserDetailsServiceImpl.java
package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.UserRequestDto;
import nl.mfarr.supernova.dtos.UserResponseDto;
import nl.mfarr.supernova.entities.UserEntity;
import nl.mfarr.supernova.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream()
                        .map(role -> "ROLE_" + role.name())
                        .toArray(String[]::new))
                .build();
    }

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequestDto.getUsername());
        userEntity.setPassword(encodedPassword);
        userEntity.setRoles(userRequestDto.getRoles());
        userRepository.save(userEntity);
        return new UserResponseDto(userEntity.getId(), userEntity.getUsername(), userEntity.getRoles());
    }
}