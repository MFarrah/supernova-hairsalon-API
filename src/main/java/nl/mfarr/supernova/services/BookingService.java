package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.*;
import nl.mfarr.supernova.entities.*;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.mappers.BookingMapper;
import nl.mfarr.supernova.repositories.*;
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

import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

    private final RosterRepository rosterRepository;
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final ValidatorService validatorService;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingService(RosterRepository rosterRepository, OrderRepository orderRepository, EmployeeRepository employeeRepository, ValidatorService validatorService, BookingRepository bookingRepository, CustomerRepository customerRepository, BookingMapper bookingMapper) {
        this.rosterRepository = rosterRepository;
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.validatorService = validatorService;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.bookingMapper = bookingMapper;
    }

    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto requestDto, Authentication authentication) {
        // Validate employee
        EmployeeEntity employee = employeeRepository.findById(requestDto.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        // Validate customer
        String currentUserEmail = authentication.getName();
        CustomerEntity customer = customerRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new IllegalArgumentException("Authenticated customerId not found"));

        // Validate orders
        Set<Long> requestedOrderIds = requestDto.getOrderIds();
        List<OrderEntity> orders = orderRepository.findAllById(requestedOrderIds);
        if (orders.size() != requestedOrderIds.size()) {
            throw new IllegalArgumentException("Some orders not found");
        }

        // Check employee qualification for orders
        Set<Long> qualifiedOrderIds = employee.getQualifiedOrderIds();
        for (OrderEntity order : orders) {
            if (!qualifiedOrderIds.contains(order.getId())) {
                throw new IllegalArgumentException("Employee is not qualified to perform all requested orders");
            }
        }

        // Check time slot availability
        List<RosterEntity> rosters = rosterRepository.findByEmployeeAndDate(employee, requestDto.getDate());
        if (rosters.isEmpty()) {
            throw new IllegalArgumentException("No roster found for the given employee's date");
        }
        RosterEntity roster = rosters.get(0);
        boolean isTimeSlotAvailable = roster.getTimeSlots().stream()
                .anyMatch(slot -> slot.getDate().equals(requestDto.getDate()) &&
                        slot.getStartTime().equals(requestDto.getStartTime()) &&
                        slot.getStatus() == TimeSlotStatus.AVAILABLE);
        if (!isTimeSlotAvailable) {
            throw new IllegalArgumentException("Requested time slot is not available");
        }

        // Calculate total cost and estimated duration
        BigDecimal totalCost = orders.stream()
                .map(OrderEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int estimatedDuration = orders.stream()
                .mapToInt(OrderEntity::getDuration)
                .sum();

        // Create booking entity
        BookingEntity booking = new BookingEntity();
        booking.setCustomerId(customer.getId());
        booking.setEmployeeId(employee.getId());
        booking.setDate(requestDto.getDate());
        booking.setStartTime(requestDto.getStartTime());
        booking.setEndTime(requestDto.getStartTime().plusMinutes(estimatedDuration));
        booking.setOrders(new HashSet<>(orders));
        booking.setEstimatedDuration(estimatedDuration);
        booking.setTotalCost(totalCost);
        booking.setStatus(BookingStatus.RESERVED);
        booking.setNotes(requestDto.getNotes());

        // Save booking
        booking = bookingRepository.save(booking);

        // Update time slot status
        roster.getTimeSlots().stream()
                .filter(slot -> slot.getDate().equals(requestDto.getDate()) &&
                        slot.getStartTime().equals(requestDto.getStartTime()))
                .forEach(slot -> slot.setStatus(TimeSlotStatus.BOOKED));
        rosterRepository.save(roster);

        // Return booking response DTO
        return bookingMapper.toResponseDto(booking);
    }
}