package nl.mfarr.supernova.services;

import jakarta.persistence.criteria.Order;
import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
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


}
