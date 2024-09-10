package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.entities.OrderEntity;

import java.time.Duration;

public class OrderMapper {

    public static OrderEntity toEntity(OrderRequestDto dto) {
        OrderEntity entity = new OrderEntity();
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setEstimatedTime(Duration.parse(dto.getEstimatedTime()));
        return entity;
    }

    public static OrderResponseDto toResponseDto(OrderEntity entity) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setOrderId(entity.getOrderId());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setEstimatedTime(entity.getEstimatedTime().toString());
        return dto;
    }
}