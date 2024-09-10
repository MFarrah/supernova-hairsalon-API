// src/main/java/nl/mfarr/supernova/controllers/AuthController.java
package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.AuthRequestDto;
import nl.mfarr.supernova.dtos.AuthResponseDto;
import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.services.UserDetailsServiceImpl;
import nl.mfarr.supernova.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Invalid email or password");
        }

        final String token = jwtUtil.generateToken(authRequestDto.getEmail());
        return new AuthResponseDto(token);
    }

    @PostMapping("/register")
    public String register(@RequestBody CustomerRequestDto customerRequestDto) {
        userDetailsService.registerCustomer(customerRequestDto);
        return "User registered successfully";
    }
}