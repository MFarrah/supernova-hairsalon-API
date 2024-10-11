package nl.mfarr.supernova.dtos.employeeDtos;

import nl.mfarr.supernova.dtos.scheduleDtos.ScheduleUpsertRequestDto;

import java.util.Set;

public class EmployeeWithScheduleUpsertRequestDto {

    private EmployeeUpsertRequestDto employee;
    private Set<ScheduleUpsertRequestDto> schedules;

    // Getters and setters
    public EmployeeUpsertRequestDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeUpsertRequestDto employee) {
        this.employee = employee;
    }

    public Set<ScheduleUpsertRequestDto> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<ScheduleUpsertRequestDto> schedules) {
        this.schedules = schedules;
    }
}
