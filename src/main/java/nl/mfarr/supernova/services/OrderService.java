package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void createOrder(OrderRequestDto orderRequestDto) {
        OrderEntity order = new OrderEntity();
        order.setDescription(orderRequestDto.getDescription());
        order.setPrice(orderRequestDto.getPrice());
        order.setEstimatedTime(orderRequestDto.getEstimatedTime());

        orderRepository.save(order);
    }

    public void updateOrder(Long orderId, OrderRequestDto orderRequestDto) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setDescription(orderRequestDto.getDescription());
        order.setPrice(orderRequestDto.getPrice());
        order.setEstimatedTime(orderRequestDto.getEstimatedTime());

        orderRepository.save(order);
    }

    public OrderEntity viewOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
