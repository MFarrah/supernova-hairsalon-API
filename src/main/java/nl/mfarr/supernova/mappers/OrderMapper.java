package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.entities.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderEntity toEntity(OrderRequestDto orderRequestDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDescription(orderRequestDto.getDescription());
        orderEntity.setPrice(orderRequestDto.getPrice());
        orderEntity.setEstimatedTime(orderRequestDto.getEstimatedTime());
        return orderEntity;
    }

    public OrderResponseDto toResponseDto(OrderEntity orderEntity) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(orderEntity.getOrderId());
        responseDto.setDescription(orderEntity.getDescription());
        responseDto.setPrice(orderEntity.getPrice());
        responseDto.setEstimatedTime(orderEntity.getEstimatedTime());
        return responseDto;
    }
}
