package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.orderDtos.OrderUpsertRequestDto;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.mappers.OrderMapper;
import nl.mfarr.supernova.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ValidatorService validatorService;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, ValidatorService validatorService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.validatorService = validatorService;
    }

    public Set<OrderEntity> findOrdersByIds(Set<Long> orderIds) {
        return new HashSet<>(orderRepository.findAllById(orderIds));
    }

    public OrderEntity createOrder(OrderUpsertRequestDto dto) {
        validatorService.validateOrderUpsertRequirements(dto);
        OrderEntity entity = orderMapper.toEntity(dto);
        return orderRepository.save(entity);
    }

    public OrderEntity updateOrder(Long id, OrderUpsertRequestDto dto) {
        validatorService.validateOrderUpsertRequirements(dto);
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + id));
        orderMapper.updateEntity(entity, dto);
        return orderRepository.save(entity);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found for ID: " + id);
        }
        orderRepository.deleteById(id);
    }
}