package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.mappers.OrderMapper;
import nl.mfarr.supernova.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponseDto createOrder(OrderRequestDto dto) {
        OrderEntity entity = OrderMapper.toEntity(dto);
        OrderEntity savedEntity = orderRepository.save(entity);
        return OrderMapper.toResponseDto(savedEntity);
    }

    public OrderResponseDto getOrderById(Long id) {
        OrderEntity entity = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderMapper.toResponseDto(entity);
    }

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public OrderResponseDto updateOrder(Long id, OrderRequestDto dto) {
        OrderEntity entity = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setEstimatedTime(Duration.parse(dto.getEstimatedTime()));
        OrderEntity updatedEntity = orderRepository.save(entity);
        return OrderMapper.toResponseDto(updatedEntity);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}