package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.dtos.OrderUpsertRequestDto;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<Set<OrderResponseDto>> getOrdersByIds(@RequestParam Set<Long> ids) {
        Set<OrderEntity> orders = orderService.findOrdersByIds(ids);
        Set<OrderResponseDto> response = orders.stream()
                .map(order -> new OrderResponseDto(order.getId(), order.getDescription(), order.getPrice(), Duration.ofMinutes(order.getDuration())))
                .collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-order")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderUpsertRequestDto dto) {
        OrderEntity order = orderService.createOrder(dto);
        OrderResponseDto response = new OrderResponseDto(order.getId(), order.getDescription(), order.getPrice(), Duration.ofMinutes(order.getDuration()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long id, @RequestBody OrderUpsertRequestDto dto) {
        OrderEntity order = orderService.updateOrder(id, dto);
        OrderResponseDto response = new OrderResponseDto(order.getId(), order.getDescription(), order.getPrice(), Duration.ofMinutes(order.getDuration()));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}