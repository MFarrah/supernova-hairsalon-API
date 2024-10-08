package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.OrderUpsertRequestDto;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import org.springframework.stereotype.Component;

import java.time.Duration;

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
        entity.setDuration(dto.getDuration().toMinutesPart());
        return entity;
    }

    public OrderResponseDto toDto(OrderEntity entity) {
        if (entity == null) {
            return null;
        }
        OrderResponseDto dto = new OrderResponseDto(entity.getId(), entity.getDescription(), entity.getPrice(), Duration.ofMinutes(entity.getDuration()));
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDuration(Duration.ofMinutes(entity.getDuration()));
        return dto;
    }

    public void updateEntity(OrderEntity entity, OrderUpsertRequestDto dto) {
        if (dto == null) {
            return;
        }
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration().toMinutesPart());
    }
}