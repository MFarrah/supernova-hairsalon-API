package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.CustomerRequestDto;
import nl.mfarr.supernova.dtos.CustomerResponseDto;
import nl.mfarr.supernova.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDto> registerCustomer(@RequestBody CustomerRequestDto requestDto) {
        return ResponseEntity.ok(customerService.registerCustomer(requestDto));
    }
}
