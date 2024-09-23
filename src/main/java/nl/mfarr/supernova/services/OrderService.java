package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.mappers.OrderMapper;
import nl.mfarr.supernova.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        OrderEntity order = orderMapper.toEntity(requestDto);
        orderRepository.save(order);
        return orderMapper.toResponseDto(order);
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public OrderResponseDto getOrderById(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order niet gevonden"));
        return orderMapper.toResponseDto(order);
    }

    // Update een bestaande order
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto requestDto) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order niet gevonden"));

        order.setDescription(requestDto.getDescription());
        order.setPrice(requestDto.getPrice());
        order.setDuration(requestDto.getDuration());

        orderRepository.save(order);
        return orderMapper.toResponseDto(order);
    }

    // Verwijder een order
    public void deleteOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order niet gevonden"));
        orderRepository.delete(order);
    }

}
