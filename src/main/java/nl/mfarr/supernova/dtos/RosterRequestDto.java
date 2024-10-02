package nl.mfarr.supernova.dtos;

import jakarta.persistence.*;
import nl.mfarr.supernova.entities.EmployeeEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.TreeSet;

public class RosterRequestDto {

    private Long id;

    private LocalDate date;

    private int month;

    private int year;

    private Set<LocalTime> timeSlots = new TreeSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public Set<LocalTime> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Set<LocalTime> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
