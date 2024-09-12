package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.mappers.OrderMapper;
import nl.mfarr.supernova.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        OrderEntity orderEntity = orderMapper.toEntity(orderRequestDto);
        orderEntity = orderRepository.save(orderEntity);
        return orderMapper.toResponseDto(orderEntity);
    }

    public Optional<OrderResponseDto> getOrderByDescription(String description) {
        return Optional.ofNullable(orderRepository.findByDescription(description))
                .map(orderMapper::toResponseDto);
    }

        public OrderEntity getOrderEntityById(Long orderId) {
            return orderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalStateException("Order not found with ID: " + orderId));
        }
    }

