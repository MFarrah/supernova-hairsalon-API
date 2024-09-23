package nl.mfarr.supernova.entities;

import jakarta.persistence.*;
import nl.mfarr.supernova.enums.BookingStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    private CustomerEntity customer;

    @ManyToOne
    private EmployeeEntity employee;

    private LocalDate bookingDate;

    private LocalTime startTime;

    private BigDecimal totalCost;

    private int totalDuration; // Totale duur van de boeking in minuten

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToMany
    private Set<TimeSlotEntity> allocatedTimeSlots; // Tijdslots toegewezen aan deze boeking

    @ManyToMany
    private Set<OrderEntity> orders; // Behandelingen gekoppeld aan deze boeking

    // Getters en Setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
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

    public Set<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderEntity> orders) {
        this.orders = orders;
    }
}
