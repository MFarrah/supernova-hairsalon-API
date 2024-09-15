package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.AuthRegisterRequestDto;
import nl.mfarr.supernova.dtos.AuthRequestDto;
import nl.mfarr.supernova.dtos.AuthResponseDto;
import nl.mfarr.supernova.security.jwt.JwtTokenProvider;
import nl.mfarr.supernova.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;



    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerCustomer(@RequestBody AuthRegisterRequestDto authRequest) {
        UserDetails userDetails = customUserDetailsService.registerCustomer(authRequest.getEmail(), authRequest.getPassword(), authRequest.getFirstName(), authRequest.getLastName(), authRequest.getPhoneNumber(), authRequest.getGender(), authRequest.getDateOfBirth());
        String token = jwtTokenProvider.generateToken(userDetails);

        AuthResponseDto response = new AuthResponseDto();
        response.setToken(token);
        response.setEmail(userDetails.getUsername());
        response.setRole(userDetails.getAuthorities().iterator().next().getAuthority());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequest) {
        logger.info("Login request received for email: {}", authRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtTokenProvider.generateToken(userDetails);

        AuthResponseDto response = new AuthResponseDto();
        response.setToken(token);
        response.setEmail(userDetails.getUsername());
        response.setRole(userDetails.getAuthorities().iterator().next().getAuthority());

        logger.info("Login successful for email: {}", authRequest.getEmail());
        return ResponseEntity.ok(response);
    }
}