package nl.mfarr.supernova.dtos.rosterDtos;

public class RosterYearRequestDto {

    private Long employeeId;
    private int year;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
