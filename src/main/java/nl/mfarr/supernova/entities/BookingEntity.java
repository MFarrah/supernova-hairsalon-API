package nl.mfarr.supernova.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import nl.mfarr.supernova.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "booking_entity")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long customerId;

    private Long employeeId;

    private LocalDate bookingDate;

    private LocalTime startTime;

    @PositiveOrZero(message = "Total cost must be positive or zero")  // Valideren dat de kosten niet negatief zijn
    private BigDecimal totalCost;

    private int totalDuration; // Totale duur van de boeking in minuten

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToMany(fetch = FetchType.LAZY)  // FetchType.LAZY toegevoegd
    private Set<TimeSlotEntity> allocatedTimeSlots; // Tijdslots toegewezen aan deze boeking

    @ManyToMany(fetch = FetchType.LAZY)  // FetchType.LAZY toegevoegd
    private Set<OrderEntity> orders; // Behandelingen gekoppeld aan deze boeking

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }



    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Set<TimeSlotEntity> getAllocatedTimeSlots() {
        return allocatedTimeSlots;
    }

    public void setAllocatedTimeSlots(Set<TimeSlotEntity> allocatedTimeSlots) {
        this.allocatedTimeSlots = allocatedTimeSlots;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
    }
}
