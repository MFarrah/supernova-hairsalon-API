package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PreAuthorize("hasRole('ADMIN, EMPLOYEE')")
    @PostMapping("/new")
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        CustomerResponseDto customerResponse = customerService.createCustomer(customerRequestDto);
        return ResponseEntity.ok(customerResponse);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponseDto> getCustomerByEmail(@PathVariable String email) {
        Optional<CustomerResponseDto> customerResponse = customerService.getCustomerByEmail(email);
        return customerResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<CustomerResponseDto> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        Optional<CustomerResponseDto> customerResponse = customerService.getCustomerByPhoneNumber(phoneNumber);
        return customerResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        Optional<CustomerResponseDto> customerResponse = customerService.getCustomerById(id);
        return customerResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/me")
    public Optional<CustomerResponseDto> getCustomerDetails(Authentication authentication) {
        // search for customer by email and return with CustomerResponseDto
        return customerService.getCustomerByEmail(authentication.getName());




    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        List<CustomerResponseDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

}
