package nl.mfarr.supernova.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class BookingRequestDto {

    private Long customerId;
    private Long employeeId;
    private LocalDate date;  // Gebruik LocalDate voor de datum
    private LocalTime startTime;  // Gebruik LocalTime voor de starttijd
    private Set<Long> orderIds;

    // Getters en Setters
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

    public Set<Long> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(Set<Long> orderIds) {
        this.orderIds = orderIds;
    }
}
