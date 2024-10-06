package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.BookingCustomerRequestDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.OrderRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final RosterRepository rosterRepository;
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final ValidatorService validatorService;

    @Autowired
    public BookingService(RosterRepository rosterRepository, OrderRepository orderRepository, EmployeeRepository employeeRepository, ValidatorService validatorService) {
        this.rosterRepository = rosterRepository;
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.validatorService = validatorService;
    }

    public BookingEntity createCustomerBooking(BookingCustomerRequestDto dto) {
        if (dto == null || dto.getDate() == null || dto.getStartTime() == null) {
            throw new IllegalArgumentException("BookingCustomerRequestDto and its date and start time cannot be null");
        }

        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found for ID: " + dto.getEmployeeId()));

        validatorService.validateQualifiedOrderIds(dto.getOrderIds(), employee.getQualifiedOrderIds());

        BookingEntity entity = new BookingEntity();
        entity.setCustomerId(dto.getCustomerId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setDate(dto.getDate());
        entity.setStartTime(dto.getStartTime());
        entity.setNotes(dto.getNotes());
        entity.setStatus(BookingStatus.RESERVED);

        List<OrderEntity> orders = dto.getOrderIds().stream()
                .map(this::fetchOrderById)
                .collect(Collectors.toList());
        entity.setOrders(orders);

        BigDecimal totalCost = orders.stream()
                .map(OrderEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        entity.setTotalCost(totalCost);

        entity.setTimeSlots(generateTimeSlots(dto.getDate(), dto.getStartTime(), entity.getEstimatedDuration()));

        return entity;
    }

    private OrderEntity fetchOrderById(Long orderId) {
        if (orderId == null) throw new IllegalArgumentException("Order ID cannot be null");

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + orderId));
    }

    private List<RosterEntity.TimeSlot> generateTimeSlots(LocalDate date, LocalTime startTime, Duration estimatedDuration) {
        if (date == null || startTime == null || estimatedDuration == null)
            throw new IllegalArgumentException("Date, startTime, and estimatedDuration cannot be null");

        List<RosterEntity.TimeSlot> allTimeSlots = fetchAllTimeSlotsForDate(date);
        List<RosterEntity.TimeSlot> requiredTimeSlots = new ArrayList<>();
        Duration accumulatedDuration = Duration.ZERO;

        for (RosterEntity.TimeSlot timeSlot : allTimeSlots) {
            if (timeSlot.getStartTime().equals(startTime) || !requiredTimeSlots.isEmpty()) {
                timeSlot.setStatus(TimeSlotStatus.BOOKED);
                requiredTimeSlots.add(timeSlot);
                accumulatedDuration = accumulatedDuration.plus(Duration.between(timeSlot.getStartTime(), timeSlot.getEndTime()));
                if (accumulatedDuration.compareTo(estimatedDuration) >= 0) break;
            }
        }

        if (accumulatedDuration.compareTo(estimatedDuration) < 0)
            throw new IllegalStateException("Not enough time slots to cover the estimated duration");

        return requiredTimeSlots;
    }

    private List<RosterEntity.TimeSlot> fetchAllTimeSlotsForDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date cannot be null");

        return rosterRepository.findAllByDate(date).stream()
                .flatMap(roster -> roster.getTimeSlots().stream())
                .collect(Collectors.toList());
    }
}