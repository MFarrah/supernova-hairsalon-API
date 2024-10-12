package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.bookingDtos.BookingEmployeeRequestDto;
import nl.mfarr.supernova.dtos.bookingDtos.BookingCustomerRequestDto;
import nl.mfarr.supernova.dtos.bookingDtos.BookingResponseDto;
import nl.mfarr.supernova.dtos.orderDtos.OrderResponseDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.repositories.BookingRepository;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public BookingEntity toEntity(BookingCustomerRequestDto dto, Set<OrderEntity> orders) {
        BookingEntity booking = new BookingEntity();

        booking.setEmployeeId(dto.getEmployeeId());
        booking.setDate(dto.getDate());
        booking.setStartTime(dto.getStartTime());
        booking.setOrders(orders);
        booking.setNotes(dto.getNotes());

        // Save the booking and link the time slots
        booking = bookingRepository.save(booking);
        linkTimeSlotsToBooking(booking);

        return booking;
    }
    public BookingEntity toEntity(BookingEmployeeRequestDto dto, Set<OrderEntity> orders) {
        BookingEntity booking = new BookingEntity();
        booking.setCustomerId(dto.getCustomerId());

        booking.setDate(dto.getDate());
        booking.setStartTime(dto.getStartTime());
        booking.setOrders(orders);
        booking.setNotes(dto.getNotes());

        // Save the booking and link the time slots
        booking = bookingRepository.save(booking);
        linkTimeSlotsToBooking(booking);

        return booking;
    }

    private void linkTimeSlotsToBooking(BookingEntity booking) {
        if (booking.getTimeSlots() != null) {
            booking.getTimeSlots().forEach(timeSlot -> timeSlot.setBooking(booking));
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

    private OrderResponseDto toOrderResponseDto(OrderEntity orderEntity) {
        return new OrderResponseDto(orderEntity.getId(), orderEntity.getDescription(),
                orderEntity.getPrice(), orderEntity.getDuration());
    }
}