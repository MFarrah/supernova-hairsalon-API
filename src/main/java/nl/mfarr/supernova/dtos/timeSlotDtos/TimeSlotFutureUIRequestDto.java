package nl.mfarr.supernova.dtos.timeSlotDtos;

import nl.mfarr.supernova.enums.TimeSlotStatus;

import java.time.LocalDate;

public class TimeSlotFutureUIRequestDto {

    LocalDate date;
    TimeSlotStatus status;

    public TimeSlotStatus getStatus() {
        return status;
    }

    public void setStatus(TimeSlotStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
