package nl.mfarr.supernova.dtos.bookingDtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class BookingEmployeeRequestDto {


    private Long customerId;
    private LocalDate date;
    private LocalTime startTime;
    private Set<Long> orderIds;
    private String notes;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
