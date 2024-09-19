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

import java.time.LocalTime;
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

        // Datum en tijd instellen vanuit BookingRequestDto
        bookingEntity.setBookingDate(bookingRequestDto.getDate());  // Gebruik LocalDate voor de boekingsdatum
        bookingEntity.setStartTime(bookingRequestDto.getStartTime());  // Gebruik LocalTime voor de begintijd

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
        responseDto.setBookingId(bookingEntity.getBookingId());
        responseDto.setCustomerId(bookingEntity.getCustomer().getCustomerId());
        responseDto.setEmployeeId(bookingEntity.getEmployee().getEmployeeId());

        // Datum en tijd apart instellen in de response DTO
        responseDto.setDate(bookingEntity.getBookingDate());  // Teruggeven als LocalDate
        responseDto.setStartTime(bookingEntity.getStartTime());  // Teruggeven als LocalTime

        // Eindtijd berekenen door de begintijd te nemen en de totale duur toe te voegen
        LocalTime endTime = bookingEntity.getStartTime().plusMinutes(bookingEntity.getTotalDuration());
        responseDto.setEndTime(endTime);  // Bereken en zet de eindtijd

        responseDto.setOrderIds(
                bookingEntity.getOrders().stream()
                        .map(order -> order.getOrderId())
                        .collect(Collectors.toSet())
        );
        responseDto.setTotalDuration(bookingEntity.getTotalDuration());
        responseDto.setTotalCost(bookingEntity.getTotalCost());
        responseDto.setStatus(bookingEntity.getStatus().toString());

        return responseDto;
    }
}
