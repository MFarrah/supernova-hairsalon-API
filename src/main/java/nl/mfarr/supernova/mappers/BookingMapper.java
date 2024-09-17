package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.BookingResponseDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.services.CustomerService;
import nl.mfarr.supernova.services.EmployeeService;
import nl.mfarr.supernova.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookingMapper {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OrderService orderService;

    // Zet BookingRequestDto om naar BookingEntity
    public BookingEntity toEntity(BookingRequestDto bookingRequestDto) {
        BookingEntity bookingEntity = new BookingEntity();

        // Klant en medewerker worden opgehaald via hun respectieve services
        bookingEntity.setCustomer(customerService.getCustomerEntityById(bookingRequestDto.getCustomerId()));
        bookingEntity.setEmployee(employeeService.getEmployeeEntityById(bookingRequestDto.getEmployeeId()));

        // Stel datum en tijd in
        bookingEntity.setBookingDate(bookingRequestDto.getDate().toLocalDate()); // BookingDate gebruik LocalDate
        bookingEntity.setStartTime(bookingRequestDto.getStartTime().toLocalTime());

        // Orders worden opgehaald via de OrderService en toegevoegd aan de boeking
        bookingEntity.setOrders(
                bookingRequestDto.getOrderIds().stream()
                        .map(orderService::getOrderEntityById)
                        .collect(Collectors.toSet())
        );

        // Standaard status voor nieuwe boekingen
        bookingEntity.setStatus(BookingStatus.PENDING);

        return bookingEntity;
    }

    // Zet BookingEntity om naar BookingResponseDto
    public BookingResponseDto toResponseDto(BookingEntity bookingEntity) {
        BookingResponseDto responseDto = new BookingResponseDto();

        // Basis boeking informatie
        responseDto.setBookingId(bookingEntity.getBookingId());
        responseDto.setCustomerId(bookingEntity.getCustomer().getCustomerId());
        responseDto.setEmployeeId(bookingEntity.getEmployee().getEmployeeId());
        responseDto.setDate(bookingEntity.getBookingDate().atStartOfDay()); // Omzetten naar LocalDateTime
        responseDto.setStartTime(bookingEntity.getStartTime().atDate(bookingEntity.getBookingDate()));

        // Zet de order IDs van de boeking
        responseDto.setOrderIds(
                bookingEntity.getOrders().stream()
                        .map(order -> order.getOrderId())
                        .collect(Collectors.toSet())
        );

        // Totale duur en kosten
        responseDto.setTotalDuration(bookingEntity.getTotalDuration());
        responseDto.setTotalCost(bookingEntity.getTotalCost());

        // Zet de status van de boeking
        responseDto.setStatus(bookingEntity.getStatus().name());

        return responseDto;
    }
}
