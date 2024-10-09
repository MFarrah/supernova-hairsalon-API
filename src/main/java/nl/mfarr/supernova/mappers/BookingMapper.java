package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.dtos.RosterResponseDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookingMapper {
    // Maps BookingRequestDto to BookingEntity
    public BookingEntity toEntity(BookingRequestDto dto, Set<OrderEntity> orders) {
        BookingEntity booking = new BookingEntity();
        booking.setEmployeeId(dto.getEmployeeId());
        booking.setDate(dto.getDate());
        booking.setStartTime(dto.getStartTime());

        booking.setOrders(orders);
        booking.setNotes(dto.getNotes());
        return booking;
    }

    public BookingResponseDto toResponseDto(BookingEntity entity) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setBookingId(entity.getId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setDate(entity.getDate());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setOrders(entity.getOrders().stream().map(this::toOrderResponseDto).collect(Collectors.toSet()));
        dto.setTotalCost(entity.getTotalCost());
        dto.setEstimatedDuration(entity.getEstimatedDuration());
        dto.setTimeSlots(entity.getTimeSlots().stream().map(this::toTimeSlotResponseDto).collect(Collectors.toList()));
        dto.setStatus(entity.getStatus().toString());
        dto.setNotes(entity.getNotes());
        return dto;
    }

    private OrderResponseDto toOrderResponseDto(OrderEntity orderEntity) {
        return new OrderResponseDto(orderEntity.getId(), orderEntity.getDescription(), orderEntity.getPrice(), Duration.ofMinutes(orderEntity.getDuration()));
    }

    private RosterResponseDto.TimeSlotDto toTimeSlotResponseDto(TimeSlotEntity timeSlot) {
        RosterResponseDto.TimeSlotDto dto = new RosterResponseDto.TimeSlotDto();
        dto.setBookedId(timeSlot.getBookedId());
        dto.setWeek(timeSlot.getWeek());
        dto.setDate(timeSlot.getDate());
        dto.setStartTime(timeSlot.getStartTime());
        dto.setEndTime(timeSlot.getEndTime());
        dto.setStatus(timeSlot.getStatus());
        return dto;
    }
}