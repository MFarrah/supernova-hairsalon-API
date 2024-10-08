package nl.mfarr.supernova.dtos;
import nl.mfarr.supernova.enums.TimeSlotStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlotResponseDto {
    private Long bookedId;
    private int week;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private TimeSlotStatus status;

    public Long getBookedId() {
        return bookedId;
    }

    public void setBookedId(Long bookedId) {
        this.bookedId = bookedId;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
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

    public TimeSlotStatus getStatus() {
        return status;
    }

    public void setStatus(TimeSlotStatus status) {
        this.status = status;
    }
}