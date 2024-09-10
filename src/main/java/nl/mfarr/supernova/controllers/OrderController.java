package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok("Order created successfully!");
    }

    @PutMapping("/update/{orderId}")
    public ResponseEntity<?> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequestDto orderRequestDto) {
        orderService.updateOrder(orderId, orderRequestDto);
        return ResponseEntity.ok("Order updated successfully!");
    }

    @GetMapping("/view/{orderId}")
    public ResponseEntity<?> viewOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.viewOrder(orderId));
    }
}
