// RosterResponseDto.java
package nl.mfarr.supernova.dtos.rosterDtos;

import nl.mfarr.supernova.dtos.timeSlotDtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.TimeSlotEntity;

import java.util.List;

public class RosterResponseDto {
    private Long id;
    private Long employeeId;
    private int month;
    private int year;
    private List<TimeSlotResponseDto> timeSlots;

    public RosterResponseDto(Long id, Long id1, int year, int month, List<TimeSlotEntity> timeSlots) {
    }

    public RosterResponseDto() {

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

    public List<TimeSlotResponseDto> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotResponseDto> timeSlots) {
        this.timeSlots = timeSlots;
    }


}