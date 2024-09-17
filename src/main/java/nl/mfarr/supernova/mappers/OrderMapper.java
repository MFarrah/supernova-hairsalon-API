package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.entities.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    // Zet OrderRequestDto om naar OrderEntity
    public OrderEntity toEntity(OrderRequestDto orderRequestDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDescription(orderRequestDto.getDescription());
        orderEntity.setPrice(orderRequestDto.getPrice());  // BigDecimal voor prijs
        orderEntity.setEstimatedDurationInMinutes(orderRequestDto.getEstimatedDurationInMinutes());  // Geschatte duur
        return orderEntity;
    }

    // Zet OrderEntity om naar OrderResponseDto
    public OrderResponseDto toResponseDto(OrderEntity orderEntity) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(orderEntity.getOrderId());
        responseDto.setDescription(orderEntity.getDescription());
        responseDto.setPrice(orderEntity.getPrice());  // BigDecimal voor prijs
        responseDto.setEstimatedDurationInMinutes(orderEntity.getEstimatedDurationInMinutes());  // Geschatte duur
        return responseDto;
    }
}
