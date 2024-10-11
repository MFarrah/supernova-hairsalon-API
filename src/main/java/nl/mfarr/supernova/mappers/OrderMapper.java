package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.orderDtos.OrderUpsertRequestDto;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.dtos.orderDtos.OrderResponseDto;
import org.springframework.stereotype.Component;



@Component
public class OrderMapper {

    public OrderMapper() {
    }

    public OrderEntity toEntity(OrderUpsertRequestDto dto) {
        if (dto == null) {
            return null;
        }
        OrderEntity entity = new OrderEntity();
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        return entity;
    }

    public OrderResponseDto toDto(OrderEntity entity) {
        if (entity == null) {
            return null;
        }
        OrderResponseDto dto = new OrderResponseDto(entity.getId(), entity.getDescription(), entity.getPrice(), entity.getDuration());
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        return dto;
    }

    public void updateEntity(OrderEntity entity, OrderUpsertRequestDto dto) {
        if (dto == null) {
            return;
        }
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
    }
}