package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.customerDtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.customerDtos.CustomerResponseDto;
import nl.mfarr.supernova.security.CustomUserDetails;
import nl.mfarr.supernova.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/batch")
    public ResponseEntity<List<CustomerResponseDto>> createCustomersBatch(@RequestBody List<CustomerRequestDto> customerRequestDtos) {
        List<CustomerResponseDto> customerResponses = customerService.createCustomersBatch(customerRequestDtos);
        return ResponseEntity.ok(customerResponses);
    }

    @PostMapping("/post")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        CustomerResponseDto customerResponse = customerService.createCustomer(customerRequestDto);
        return ResponseEntity.ok(customerResponse);
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDto> getCustomerByEmail(@PathVariable String email) {
        Optional<CustomerResponseDto> customerResponse = customerService.getCustomerByEmail(email);
        return customerResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<CustomerResponseDto> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        Optional<CustomerResponseDto> customerResponse = customerService.getCustomerByPhoneNumber(phoneNumber);
        return customerResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !userId.equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<CustomerResponseDto> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/all")
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<CustomerResponseDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

}
