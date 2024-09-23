package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.entities.*;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.mappers.BookingMapper;
import nl.mfarr.supernova.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

@Service
public class BookingService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TimeSlotService timeSlotService;

    @Autowired
    private BookingMapper bookingMapper;

    public BookingResponseDto createBooking(BookingRequestDto bookingRequestDto) {
        CustomerEntity customer = customerRepository.findById(bookingRequestDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Klant niet gevonden"));

        EmployeeEntity employee = employeeRepository.findById(bookingRequestDto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Medewerker niet gevonden"));

        Set<OrderEntity> orders = bookingRequestDto.getOrderIds().stream()
                .map(orderRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .collect(Collectors.toSet());

        BigDecimal totalCost = orders.stream()
                .map(OrderEntity::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalDuration = orders.stream()
                .mapToInt(OrderEntity::getDuration)
                .sum();

        // Check if employee is qualified for all orders
        boolean isQualified = orders.stream().allMatch(order -> employee.getQualifiedOrders().contains(order));
        if (!isQualified) {
            throw new RuntimeException("Medewerker is niet gekwalificeerd voor alle behandelingen");
        }

        if (!timeSlotService.isEmployeeAvailable(employee.getEmployeeId(), bookingRequestDto.getDate(),
                bookingRequestDto.getStartTime(), totalDuration)) {
            throw new RuntimeException("Medewerker is niet beschikbaar");
        }

        Set<TimeSlotEntity> allocatedSlots = new HashSet<>(timeSlotService.allocateTimeSlots(
                employee.getEmployeeId(), bookingRequestDto.getDate(), bookingRequestDto.getStartTime(), totalDuration));

        BookingEntity booking = bookingMapper.toEntity(bookingRequestDto);
        booking.setCustomer(customer);
        booking.setEmployee(employee);
        booking.setTotalCost(totalCost);
        booking.setTotalDuration(totalDuration);
        booking.setAllocatedTimeSlots(allocatedSlots);
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);
        return bookingMapper.toResponseDto(booking);
    }

    // Haal boekingen op van een klant
    public List<BookingResponseDto> getBookingsByCustomer(Long customerId) {
        List<BookingEntity> bookings = bookingRepository.findByCustomerCustomerIdAndStatus(customerId, "PENDING");
        return bookings.stream()
                .map(bookingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    // Haal boekingen op van een medewerker
    public List<BookingResponseDto> getBookingsByEmployee(Long employeeId) {
        List<BookingEntity> bookings = bookingRepository.findByEmployeeEmployeeIdAndStatus(employeeId, "PENDING");
        return bookings.stream()
                .map(bookingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    // Annuleer een boeking
    public BookingResponseDto cancelBooking(Long bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Boeking niet gevonden"));
        booking.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(booking);
        return bookingMapper.toResponseDto(booking);
    }

    // Werk de status van een boeking bij
    public BookingResponseDto updateBookingStatus(Long bookingId, String status) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Boeking niet gevonden"));

        booking.setStatus(BookingStatus.valueOf(status.toUpperCase()));
        bookingRepository.save(booking);
        return bookingMapper.toResponseDto(booking);
    }
}