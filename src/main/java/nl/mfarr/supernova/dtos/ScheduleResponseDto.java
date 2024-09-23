package nl.mfarr.supernova.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class ScheduleResponseDto {

    private Long scheduleId;
    private Long employeeId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Set<TimeSlotResponseDto> timeSlots;
    private String dayOfWeek;

    // Getters and Setters
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
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

    public Set<TimeSlotResponseDto> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Set<TimeSlotResponseDto> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}