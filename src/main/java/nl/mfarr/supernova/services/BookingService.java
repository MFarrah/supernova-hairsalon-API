package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.*;
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

        CustomerEntity customer = customerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        EmployeeEntity employee = employeeRepository.findById(requestDto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        RosterEntity roster = rosterRepository.findByEmployeeAndDate(employee, requestDto.getDate()).stream()
                .findFirst()
                .orElseThrow(() -> new NoRostersFoundEmployeeDateException("No rosters found for the given employee's date"));

        Set<OrderEntity> orders = requestDto.getOrderIds().stream()
                .map(orderId -> orderRepository.findById(orderId)
                        .orElseThrow(() -> new OrderNotFoundException("Order not found")))
                .collect(Collectors.toSet());

        BigDecimal totalCost = orders.stream()
                .map(OrderEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int estimatedDuration = orders.stream()
                .mapToInt(OrderEntity::getDuration)
                .sum();

        LocalTime endTime = requestDto.getStartTime().plusMinutes(estimatedDuration);

        List<TimeSlotEntity> availableSlots = roster.getTimeSlots().stream()
                .filter(slot -> slot.getDate().equals(requestDto.getDate()) &&
                        slot.getStartTime().isAfter(requestDto.getStartTime().minusMinutes(1)) &&
                        slot.getEndTime().isBefore(endTime.plusMinutes(1)) &&
                        slot.getStatus() == TimeSlotStatus.AVAILABLE)
                .toList();

        BookingEntity booking = new BookingEntity();
        booking.setCustomerId(customer.getId());
        booking.setEmployeeId(employee.getId());
        booking.setDate(requestDto.getDate());
        booking.setStartTime(requestDto.getStartTime());
        booking.setEndTime(endTime);
        booking.setOrders(new HashSet<>(orders));
        booking.setEstimatedDuration(estimatedDuration);
        booking.setTimeSlots(availableSlots);
        booking.setTotalCost(totalCost);
        booking.setStatus(BookingStatus.RESERVED);
        booking.setNotes(requestDto.getNotes());

        BookingEntity savedBooking = bookingRepository.save(booking);

        availableSlots.forEach(slot -> {
            slot.setStatus(TimeSlotStatus.BOOKED);
            slot.setBookedId(savedBooking.getId());
        });
        rosterRepository.save(roster);

        return bookingMapper.toResponseDto(savedBooking);
    }
}