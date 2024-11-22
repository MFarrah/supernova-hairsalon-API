package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.OrderRequestDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.models.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDto toDto(OrderEntity orderEntity) {
        OrderResponseDto orderDto = new OrderResponseDto();
        orderDto.setOrderId(orderEntity.getOrderId());
        orderDto.setTreatment(orderEntity.getTreatment());
        orderDto.setPrice(orderEntity.getPrice());
        orderDto.setDuration(orderEntity.getDuration());
        orderDto.setBookingId(orderEntity.getBooking().getBookingId());
        return orderDto;
    }

    public OrderEntity toEntity(OrderRequestDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTreatment(orderDto.getTreatment());
        orderEntity.setPrice(orderDto.getPrice());
        orderEntity.setDuration(orderDto.getDuration());
        return orderEntity;
    }
}
