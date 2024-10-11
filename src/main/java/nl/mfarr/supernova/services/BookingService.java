package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.bookingDtos.BookingEmployeeRequestDto;
import nl.mfarr.supernova.dtos.bookingDtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.bookingDtos.BookingResponseDto;
import nl.mfarr.supernova.entities.*;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.*;
import nl.mfarr.supernova.mappers.BookingMapper;
import nl.mfarr.supernova.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
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
    public BookingResponseDto createCustomerBooking(BookingRequestDto requestDto, Authentication authentication) {

        // Fetch the customer entity using the email from the authentication object
        CustomerEntity customer = customerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        // Fetch the employee entity using the employee ID from the request DTO
        EmployeeEntity employee = employeeRepository.findById(requestDto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        // Fetch the roster entity for the given employee and date
        RosterEntity roster = rosterRepository.findByEmployeeIdAndMonthAndYear(employee.getId(), requestDto.getDate().getMonthValue(), requestDto.getDate().getYear())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RosterNotFoundException("Roster not found"));


        // Fetch the order entities using the order IDs from the request DTO
        Set<OrderEntity> orders = requestDto.getOrderIds().stream()
                .map(orderId -> orderRepository.findById(orderId)
                        .orElseThrow(() -> new OrderNotFoundException("Order not found")))
                .collect(Collectors.toSet());

        // Calculate the total cost of the orders
        BigDecimal totalCost = orders.stream()
                .map(OrderEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate the estimated duration of the orders
        int estimatedDuration = orders.stream()
                .mapToInt(OrderEntity::getDuration)
                .sum();

        // Calculate the end time of the booking
        LocalTime endTime = requestDto.getStartTime().plusMinutes(estimatedDuration);

        // Fetch the available time slots for the given date and time range
        List<TimeSlotEntity> availableSlots = roster.getTimeSlots().stream()
                .filter(slot -> slot.getDate().equals(requestDto.getDate()) &&
                        slot.getStartTime().isAfter(requestDto.getStartTime().minusMinutes(1)) &&
                        slot.getEndTime().isBefore(endTime.plusMinutes(15)) &&
                        slot.getStatus() == TimeSlotStatus.AVAILABLE)
                .toList();

        // Create a new booking entity and set its properties
        BookingEntity booking = new BookingEntity();
        booking.setEmployeeId(employee.getId());
        booking.setCustomerId(requestDto.getEmployeeId());
        booking.setDate(requestDto.getDate());
        booking.setStartTime(requestDto.getStartTime());
        booking.setEndTime(endTime);
        booking.setOrders(new HashSet<>(orders));
        booking.setEstimatedDuration(estimatedDuration);
        booking.setTotalCost(totalCost);
        booking.setNotes(requestDto.getNotes());
        booking.setTimeSlots(availableSlots);
        booking.setStatus(BookingStatus.RESERVED);

        BookingEntity savedBooking = bookingRepository.save(booking);

        // Update the status of the available time slots to BOOKED and set the booking ID
        availableSlots.forEach(slot -> {
            slot.setStatus(TimeSlotStatus.BOOKED);
            slot.setBookingId(savedBooking.getId());
        });

        // Save the updated roster entity to the database
        rosterRepository.save(roster);

        // Return the booking response DTO
        return bookingMapper.toResponseDto(savedBooking);
    }

    @Transactional
    public BookingResponseDto createEmployeeBooking(BookingEmployeeRequestDto requestDto, Authentication authentication) {

        // Fetch the employee entity using the email from the authentication object
        EmployeeEntity employee = employeeRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        // Check if the employee is qualified for all the orders
        Set<OrderEntity> orders = requestDto.getOrderIds().stream()
                .map(orderId -> orderRepository.findById(orderId)
                        .orElseThrow(() -> new OrderNotFoundException("Order not found")))
                .collect(Collectors.toSet());

        Set<Long> qualifiedOrderIds = employee.getQualifiedOrders().stream()
                .map(OrderEntity::getId)
                .collect(Collectors.toSet());

        for (OrderEntity order : orders) {
            if (!qualifiedOrderIds.contains(order.getId())) {
                throw new EmployeeNotQualifiedException("Employee is not qualified for one or more orders.");
            }
        }

        // Fetch the roster entity for the given employee and date
        RosterEntity roster = rosterRepository.findByEmployeeIdAndMonthAndYear(employee.getId(), requestDto.getDate().getMonthValue(), requestDto.getDate().getYear())
                .stream()
                .findFirst()
                .orElseThrow(() -> new RosterNotFoundException("Roster not found"));

        // Calculate the total cost of the orders
        BigDecimal totalCost = orders.stream()
                .map(OrderEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate the estimated duration of the orders
        int estimatedDuration = orders.stream()
                .mapToInt(OrderEntity::getDuration)
                .sum();

        // Calculate the end time of the booking
        LocalTime endTime = requestDto.getStartTime().plusMinutes(estimatedDuration);

        // Fetch the available time slots for the given date and time range
        List<TimeSlotEntity> availableSlots = roster.getTimeSlots().stream()
                .filter(slot -> slot.getDate().equals(requestDto.getDate()) &&
                        slot.getStartTime().isAfter(requestDto.getStartTime().minusMinutes(1)) &&
                        slot.getEndTime().isBefore(endTime.plusMinutes(15)) &&
                        slot.getStatus() == TimeSlotStatus.AVAILABLE)
                .toList();

        // Check if there are enough available time slots for the estimated duration
        int availableDuration = availableSlots.stream()
                .mapToInt(slot -> (int) java.time.Duration.between(slot.getStartTime(), slot.getEndTime()).toMinutes())
                .sum();

        if (availableDuration < estimatedDuration) {
            throw new InsufficientTimeSlotsException("Not enough available time slots for the requested booking duration.");
        }

        // Create a new booking entity and set its properties
        BookingEntity booking = new BookingEntity();
        booking.setEmployeeId(employee.getId());
        booking.setCustomerId(requestDto.getCustomerId());
        booking.setDate(requestDto.getDate());
        booking.setStartTime(requestDto.getStartTime());
        booking.setEndTime(endTime);
        booking.setOrders(new HashSet<>(orders));
        booking.setEstimatedDuration(estimatedDuration);
        booking.setTotalCost(totalCost);
        booking.setNotes(requestDto.getNotes());
        booking.setTimeSlots(availableSlots);
        booking.setStatus(BookingStatus.CONFIRMED);

        BookingEntity savedBooking = bookingRepository.save(booking);

        // Update the status of the available time slots to BOOKED and set the booking ID
        availableSlots.forEach(slot -> {
            slot.setStatus(TimeSlotStatus.BOOKED);
            slot.setBookingId(savedBooking.getId());
        });

        // Save the updated roster entity to the database
        rosterRepository.save(roster);

        // Return the booking response DTO
        return bookingMapper.toResponseDto(savedBooking);
    }

}