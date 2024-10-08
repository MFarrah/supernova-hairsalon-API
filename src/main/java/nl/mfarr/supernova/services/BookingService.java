package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.BookingCustomerRequestDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.repositories.BookingRepository;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.OrderRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final RosterRepository rosterRepository;
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final ValidatorService validatorService;
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(RosterRepository rosterRepository, OrderRepository orderRepository, EmployeeRepository employeeRepository, ValidatorService validatorService, BookingRepository bookingRepository) {
        this.rosterRepository = rosterRepository;
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.validatorService = validatorService;
        this.bookingRepository = bookingRepository;
    }

    public BookingEntity createBooking(BookingCustomerRequestDto dto) {

        validatorService.validateBookingRequest(dto);
        BookingEntity entity = initializeBookingEntity(dto);

        EmployeeEntity employee = fetchEmployeeById(dto.getEmployeeId());
        entity.setEmployeeId(employee.getId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        entity.setCustomerId((Long) authentication.getPrincipal());

        Set<Long> orderIdsSet = new HashSet<>(dto.getOrderIds());
        validatorService.validateQualifiedOrderIds((List<Long>) orderIdsSet, employee.getQualifiedOrderIds());
        List<OrderEntity> orders = fetchOrdersByIds(orderIdsSet);
        orders.forEach(order -> entity.getOrders().add(order));
        entity.setOrders(new HashSet<>(orders));

        for List<OrderEntity> order : orders) {
            entity.setEstimatedDuration(calculateTotalDuration(order));
            entity.setTotalCost(calculateTotalCost(order));
        }

    }

    private Duration calculateTotalDuration(List<OrderEntity> orders) {
        if (orders == null || orders.isEmpty()) {
            throw new IllegalArgumentException("Order list cannot be null or empty");
        }
        return orders.stream()
                .map(OrderEntity::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    public BigDecimal calculateTotalCost(List<OrderEntity> orders) {
        if (orders == null || orders.isEmpty()) {
            throw new IllegalArgumentException("Order list cannot be null or empty");
        }
        return orders.stream()
                .map(OrderEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private EmployeeEntity fetchEmployeeById(Long employeeId) {
        if (employeeId == null) throw new IllegalArgumentException("Employee ID cannot be null");

        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found for ID: " + employeeId));
    }

    private BookingEntity initializeBookingEntity(BookingCustomerRequestDto dto) {
        BookingEntity entity = new BookingEntity();
        entity.setCustomerId(dto.getCustomerId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setDate(dto.getDate());
        entity.setStartTime(dto.getStartTime());
        entity.setNotes(dto.getNotes());
        entity.setStatus(BookingStatus.RESERVED);
        return entity;
    }

    private List<OrderEntity> fetchOrdersByIds(Set<Long> orderIds) {
        if (orderIds == null || orderIds.isEmpty()) {
            throw new IllegalArgumentException("Order IDs cannot be null or empty");
        }

        return orderIds.stream()
                .map(this::fetchOrderById)
                .collect(Collectors.toList());
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

    public LocalTime calculateEndTime(LocalTime startTime, Duration estimatedDuration) {
        if (startTime == null || estimatedDuration == null)
            throw new IllegalArgumentException("Start time and estimated duration cannot be null");

        return startTime.plus(estimatedDuration);
    }
}