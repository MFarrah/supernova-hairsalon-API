package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.mappers.OrderMapper;
import nl.mfarr.supernova.models.OrderEntity;
import nl.mfarr.supernova.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<OrderResponseDto> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto);
    }

    public OrderResponseDto createOrder(OrderRequestDto orderDto) {
        OrderEntity orderEntity = orderMapper.toEntity(orderDto);
        OrderEntity savedEntity = orderRepository.save(orderEntity);
        return orderMapper.toDto(savedEntity);
    }

    public OrderResponseDto updateOrder(OrderRequestDto orderDto) {
        OrderEntity orderEntity = orderMapper.toEntity(orderDto);
        OrderEntity updatedEntity = orderRepository.save(orderEntity);
        return orderMapper.toDto(updatedEntity);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
