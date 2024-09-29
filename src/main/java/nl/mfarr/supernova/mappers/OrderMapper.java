package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.OrderUpsertRequestDto;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public static OrderEntity toEntity(OrderRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, OrderEntity.class);
    }

    public static OrderEntity toEntity(OrderUpsertRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, OrderEntity.class);
    }

    public OrderResponseDto toDto(OrderEntity entity) {
        return GenericMapperHelper.mapToDto(entity, OrderResponseDto.class);
    }


}
