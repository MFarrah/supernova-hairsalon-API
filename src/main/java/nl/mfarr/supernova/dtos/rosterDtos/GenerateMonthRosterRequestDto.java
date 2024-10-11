
package nl.mfarr.supernova.dtos.rosterDtos;

import nl.mfarr.supernova.enums.TimeSlotStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public class GenerateMonthRosterRequestDto {
    private Long employeeId;
    private int month;
    private int year;

    public static class TimeSlotResponseDto {
        private int week;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private TimeSlotStatus status;

        // Getters and Setters

        public void setWeek(int week) {
            this.week = week;
        }

        public int getWeek() {
            return week;
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
}