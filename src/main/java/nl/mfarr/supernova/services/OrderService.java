package nl.mfarr.supernova.services;

import jakarta.persistence.criteria.Order;
import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.dtos.OrderUpsertRequestDto;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.mappers.OrderMapper;
import nl.mfarr.supernova.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    public Set<OrderEntity> findOrdersByIds(Set<Long> orderIds) {
        return orderRepository.findAllById(orderIds).stream().collect(Collectors.toSet());
    }

    public OrderEntity createOrder(OrderUpsertRequestDto dto) {
        OrderEntity entity = orderMapper.toEntity(dto);
        return orderRepository.save(entity);
    }

    public OrderEntity updateOrder(Long id, OrderUpsertRequestDto dto) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + id));
        orderMapper.updateEntity(entity, dto);
        return orderRepository.save(entity);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


}
