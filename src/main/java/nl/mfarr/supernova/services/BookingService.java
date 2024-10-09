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

        List<RosterEntity.TimeSlot> availableSlots = roster.getTimeSlots().stream()
                .filter(slot -> slot.getDate().equals(requestDto.getDate()) &&
                        slot.getStartTime().isAfter(requestDto.getStartTime().minusMinutes(1)) &&
                        slot.getEndTime().isBefore(endTime.plusMinutes(1)) &&
                        slot.getStatus() == TimeSlotStatus.AVAILABLE)
                .toList();

        // Maak een nieuwe booking
        BookingEntity booking = new BookingEntity();

        // Zet de customer van de booking, afhankelijk van of de ingelogde customer dezelfde is als in de request
        if (customer.getId() != requestDto.getCustomerId()) {
            CustomerEntity assignedCustomer = customerRepository.findById(requestDto.getCustomerId())
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
            booking.setCustomer(assignedCustomer);
        } else {
            booking.setCustomer(customer);
        }

        // Stel de eerder opgehaalde employee in de booking in
        booking.setEmployee(employee);

        booking.setDate(requestDto.getDate());
        booking.setStartTime(requestDto.getStartTime());
        booking.setEndTime(endTime);
        booking.setOrders(new HashSet<>(orders));
        booking.setEstimatedDuration(estimatedDuration);
        booking.setTimeSlots(availableSlots);
        booking.setTotalCost(totalCost);
        booking.setStatus(BookingStatus.RESERVED);
        booking.setNotes(requestDto.getNotes());

        // Sla de booking op
        BookingEntity savedBooking = bookingRepository.save(booking);

        // Update de tijdslots naar BOOKED
        availableSlots.forEach(slot -> {
            slot.setStatus(TimeSlotStatus.BOOKED);
            slot.setBookingId(savedBooking.getId());
        });

        // Sla het geüpdatete rooster op
        rosterRepository.save(roster);

        // Return de response DTO
        return bookingMapper.toResponseDto(savedBooking);
    }
}