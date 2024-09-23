package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.entities.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderEntity toEntity(OrderRequestDto dto) {
        OrderEntity entity = new OrderEntity();
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        return entity;
    }

    public OrderResponseDto toResponseDto(OrderEntity entity) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(entity.getOrderId());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        return dto;
    }
}
