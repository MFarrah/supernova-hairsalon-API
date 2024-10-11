package nl.mfarr.supernova.dtos;

public class GetRosterRequestDto {

    private Long employeeId;
    private int month;
    private int year;

    public GetRosterRequestDto() {
    }

    public GetRosterRequestDto(Long employeeId, int month, int year) {
        this.employeeId = employeeId;
        this.month = month;
        this.year = year;
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
}
