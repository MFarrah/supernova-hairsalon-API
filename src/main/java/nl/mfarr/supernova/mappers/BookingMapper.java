package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.dtos.OrderResponseDto;
import nl.mfarr.supernova.dtos.RosterResponseDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookingMapper {
    private final EmployeeRepository employeeRepository;

    public BookingMapper(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Maps BookingRequestDto to BookingEntity
    public BookingEntity toEntity(BookingRequestDto dto, Set<OrderEntity> orders) {
        BookingEntity booking = new BookingEntity();
        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        booking.setEmployee(employee);
        booking.setDate(dto.getDate());
        booking.setStartTime(dto.getStartTime());

        booking.setOrders(orders);
        booking.setNotes(dto.getNotes());
        return booking;
    }

    public BookingResponseDto toResponseDto(BookingEntity entity) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setBookingId(entity.getId());
        dto.setCustomerId(entity.getCustomer().getId());
        dto.setEmployeeId(entity.getEmployee().getId());
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

    private RosterResponseDto.TimeSlotDto toTimeSlotResponseDto(RosterEntity.TimeSlot timeSlot) {
        RosterResponseDto.TimeSlotDto dto = new RosterResponseDto.TimeSlotDto();
        dto.setBookedId(timeSlot.getBookingId());
        dto.setWeek(timeSlot.getWeek());
        dto.setDate(timeSlot.getDate());
        dto.setStartTime(timeSlot.getStartTime());
        dto.setEndTime(timeSlot.getEndTime());
        dto.setStatus(timeSlot.getStatus());
        return dto;
    }
}