// RosterResponseDto.java
package nl.mfarr.supernova.dtos;

import nl.mfarr.supernova.enums.TimeSlotStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RosterResponseDto {
    private Long id;
    private Long employeeId;
    private int month;
    private int year;
    private List<TimeSlotDto> timeSlots;

    public static class TimeSlotDto {
        private Long bookedId;
        private int week;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private TimeSlotStatus status;

        // Getters and Setters


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

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<TimeSlotDto> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotDto> timeSlots) {
        this.timeSlots = timeSlots;
    }
}