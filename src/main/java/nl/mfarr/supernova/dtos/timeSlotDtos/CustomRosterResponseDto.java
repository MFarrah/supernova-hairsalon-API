package nl.mfarr.supernova.dtos.rosterDtos;

import nl.mfarr.supernova.dtos.timeSlotDtos.TimeSlotResponseDto;

import java.util.List;

public class CustomRosterResponseDto {
    private Long rosterId;
    private Long employeeId;
    private List<TimeSlotResponseDto> timeSlots;

    // Getters and Setters
    public Long getRosterId() {
        return rosterId;
    }

    public void setRosterId(Long rosterId) {
        this.rosterId = rosterId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public List<TimeSlotResponseDto> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotResponseDto> timeSlots) {
        this.timeSlots = timeSlots;
    }
}