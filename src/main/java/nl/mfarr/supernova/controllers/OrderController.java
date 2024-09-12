package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponse = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<OrderResponseDto> getOrderByDescription(@PathVariable String description) {
        Optional<OrderResponseDto> orderResponse = orderService.getOrderByDescription(description);
        return orderResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
