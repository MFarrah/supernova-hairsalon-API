package nl.mfarr.supernova.dtos.bookingDtos;

import nl.mfarr.supernova.dtos.orderDtos.OrderResponseDto;
import nl.mfarr.supernova.dtos.timeSlotDtos.TimeSlotResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class BookingResponseDto {

    private Long bookingId;
    private Long customerId;
    private Long employeeId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Set<OrderResponseDto> orders;
    private BigDecimal totalCost;
    private int estimatedDuration;
    private String notes;
    private String status;
    private List<TimeSlotResponseDto> timeSlots;

    // Getters en Setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Set<OrderResponseDto> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderResponseDto> orders) {
        this.orders = orders;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public int getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(int estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<TimeSlotResponseDto> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotResponseDto> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
