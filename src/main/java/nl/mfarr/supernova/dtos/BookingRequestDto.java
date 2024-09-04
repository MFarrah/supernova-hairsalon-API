package nl.mfarr.supernova.dtos;

import nl.mfarr.supernova.enums.StatusEnum;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingRequestDto {
    private LocalDate date;
    private LocalTime time;
    private StatusEnum status;
    private Long customerId;
    private Long employeeId;

    public BookingRequestDto() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
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
}



