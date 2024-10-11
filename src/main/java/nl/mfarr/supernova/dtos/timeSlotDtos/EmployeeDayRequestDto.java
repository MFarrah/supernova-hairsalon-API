package nl.mfarr.supernova.dtos.timeSlotDtos;

import java.time.LocalDate;

public class EmployeeDayRequestDto {

    private Long employeeId;
    private LocalDate date;


    public EmployeeDayRequestDto(Long employeeId, LocalDate date) {
        this.employeeId = employeeId;
        this.date = date;
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


}
