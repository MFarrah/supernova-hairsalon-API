package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.entities.CustomerEntity;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.OrderEntity;

import java.util.stream.Collectors;

public class BookingMapper {

    public static BookingEntity toEntity(BookingRequestDto dto, CustomerEntity customer, EmployeeEntity employee) {
        BookingEntity entity = new BookingEntity();
        entity.setCustomer(customer);
        entity.setEmployee(employee);
        entity.setDate(dto.getDate());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        entity.setOrders(dto.getOrderIds().stream().map(orderId -> {
            OrderEntity order = new OrderEntity();
            order.setOrderId(orderId);
            return order;
        }).collect(Collectors.toSet()));
        return entity;
    }

    public static BookingResponseDto toResponseDto(BookingEntity entity) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setBookingId(entity.getBookingId());
        dto.setCustomerId(entity.getCustomer().getCustomerId());
        dto.setEmployeeId(entity.getEmployee().getEmployeeId());
        dto.setDate(entity.getDate());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setOrderIds(entity.getOrders().stream().map(OrderEntity::getOrderId).collect(Collectors.toSet()));
        dto.setTotalDuration(entity.getTotalDuration());
        dto.setTotalCost(entity.getTotalCost());
        dto.setStatus(entity.getStatus().name());
        return dto;
    }
}