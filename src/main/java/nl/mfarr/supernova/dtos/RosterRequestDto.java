package nl.mfarr.supernova.dtos;

import jakarta.persistence.*;
import nl.mfarr.supernova.entities.EmployeeEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class RosterRequestDto {

    private Long employeeId;

    private int week;
    private int month;
    private int year;
    private List<TimeSlotRequestDto> timeSlots;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
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

    public List<TimeSlotRequestDto> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotRequestDto> timeSlots) {
        this.timeSlots = timeSlots;
    }
}


