package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.BookingCustomerRequestDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    private final GenericMapperHelper genericMapperHelper;

    @Autowired
    public BookingMapper(GenericMapperHelper genericMapperHelper) {
        this.genericMapperHelper = genericMapperHelper;
    }

    public BookingEntity toEntity(BookingCustomerRequestDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("BookingCustomerRequestDto cannot be null");
        }

        BookingEntity entity = new BookingEntity();
        genericMapperHelper.mapToEntity(dto, entity);
        entity.setStatus(BookingStatus.RESERVED); // Default status, adjust as needed

        List<OrderEntity> orders = dto.getOrderIds().stream()
                .map(this::fetchOrderById)
                .collect(Collectors.toList());
        entity.setOrders(orders);

        List<RosterEntity.TimeSlot> timeSlots = generateTimeSlots(dto.getDate(), dto.getStartTime(), entity.getEstimatedDuration());
        entity.setTimeSlots(timeSlots);

        return entity;
    }

    public BookingCustomerRequestDto toDto(BookingEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("BookingEntity cannot be null");
        }

        BookingCustomerRequestDto dto = new BookingCustomerRequestDto();
        genericMapperHelper.mapToDto(entity, dto);

        List<Long> orderIds = entity.getOrders().stream()
                .map(OrderEntity::getId)
                .collect(Collectors.toList());
        dto.setOrderIds(orderIds);

        return dto;
    }

    private OrderEntity fetchOrderById(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        // Implement this method to fetch OrderEntity by ID
        // For example, using an OrderRepository
        return new OrderEntity(); // Placeholder
    }

    private List<RosterEntity.TimeSlot> generateTimeSlots(LocalDate date, LocalTime startTime, Duration estimatedDuration) {
        if (date == null || startTime == null || estimatedDuration == null) {
            throw new IllegalArgumentException("Date, startTime, and estimatedDuration cannot be null");
        }

        List<RosterEntity.TimeSlot> allTimeSlots = fetchAllTimeSlotsForDate(date); // Fetch all time slots for the given date
        List<RosterEntity.TimeSlot> requiredTimeSlots = new ArrayList<>();
        Duration accumulatedDuration = Duration.ZERO;

        for (RosterEntity.TimeSlot timeSlot : allTimeSlots) {
            if (timeSlot.getStartTime().equals(startTime) || !requiredTimeSlots.isEmpty()) {
                timeSlot.setStatus(TimeSlotStatus.BOOKED); // Set status to BOOKED
                requiredTimeSlots.add(timeSlot);
                accumulatedDuration = accumulatedDuration.plus(Duration.between(timeSlot.getStartTime(), timeSlot.getEndTime()));
                if (accumulatedDuration.compareTo(estimatedDuration) >= 0) {
                    break;
                }
            }
        }

        if (accumulatedDuration.compareTo(estimatedDuration) < 0) {
            throw new IllegalStateException("Not enough time slots to cover the estimated duration");
        }

        return requiredTimeSlots;
    }

    private List<RosterEntity.TimeSlot> fetchAllTimeSlotsForDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        // Implement this method to fetch all time slots for the given date
        return List.of(); // Placeholder
    }
}