package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.*;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.repositories.BookingRepository;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    @Autowired
    private TimeSlotMapper timeSlotMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    public BookingEntity toEntity(BookingRequestDto dto, Set<OrderEntity> orders) {
        // Maak de BookingEntity en stel de velden in
        BookingEntity booking = new BookingEntity();
        booking.setEmployeeId(dto.getEmployeeId());
        booking.setDate(dto.getDate());
        booking.setStartTime(dto.getStartTime());
        booking.setOrders(orders);
        booking.setNotes(dto.getNotes());

        // Sla de booking op en koppel de timeSlots
        booking = bookingRepository.save(booking);

        // TimeSlots koppelen aan booking
        linkTimeSlotsToBooking(booking);

        return booking;
    }

    // Helper functie om timeSlots te koppelen
    private void linkTimeSlotsToBooking(BookingEntity booking) {
        if (booking.getTimeSlots() != null) {
            booking.getTimeSlots().forEach(timeSlot -> timeSlot.setBookingId(booking.getId()));
        }
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
        dto.setTimeSlots(entity.getTimeSlots().stream().map(timeSlotMapper::toDto).collect(Collectors.toList()));
        dto.setTotalCost(entity.getTotalCost());
        dto.setEstimatedDuration(entity.getEstimatedDuration());
        dto.setStatus(entity.getStatus().toString());
        dto.setNotes(entity.getNotes());

        return dto;
    }

    // Mapping van een order
    private OrderResponseDto toOrderResponseDto(OrderEntity orderEntity) {
        return new OrderResponseDto(orderEntity.getId(), orderEntity.getDescription(),
                orderEntity.getPrice(), Duration.ofMinutes(orderEntity.getDuration()));
    }
}
