package nl.mfarr.supernova.dtos.timeSlotDtos;

import java.time.LocalDate;

public class EmployeeDayRequestDto {

    private Long employeeId;
    private LocalDate date;
    private int year;

    public EmployeeDayRequestDto(Long employeeId, LocalDate date, int year) {
        this.employeeId = employeeId;
        this.date = date;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
