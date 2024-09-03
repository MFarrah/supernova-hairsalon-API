package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CustomerResponseDto>> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        return ResponseEntity.ok(customerService.createCustomer(customerRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDto customerRequestDto) {
        return ResponseEntity.ok(customerService.updateCustomer(customerRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
