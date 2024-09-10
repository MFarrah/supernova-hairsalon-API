package nl.mfarr.supernova.controllers;


import nl.mfarr.supernova.dtos.AuthRequestDto;
import nl.mfarr.supernova.dtos.AuthResponseDto;
import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.services.UserDetailsServiceImpl;
import nl.mfarr.supernova.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint for customer registration
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        try {
            CustomerEntity customer = new CustomerEntity();
            customer.setEmail(customerRequestDto.getEmail());
            customer.setPassword(customerRequestDto.getPassword());
            customer.setFirstName(customerRequestDto.getFirstName());
            customer.setLastName(customerRequestDto.getLastName());
            customer.setDateOfBirth(customerRequestDto.getDateOfBirth());
            customer.setGender(customerRequestDto.getGender());
            customer.setPhoneNumber(customerRequestDto.getPhoneNumber());

            userDetailsService.registerCustomer(customer);

            return ResponseEntity.ok("Customer registered successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // Endpoint for login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword())
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDto.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponseDto(jwt));
    }
}
