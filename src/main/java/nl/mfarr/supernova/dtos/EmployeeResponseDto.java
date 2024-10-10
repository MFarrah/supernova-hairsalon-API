package nl.mfarr.supernova.dtos;

import nl.mfarr.supernova.enums.Gender;

import java.time.LocalDate;
import java.util.Set;

public class EmployeeResponseDto {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private Set<Long> qualifiedOrderIds;
    private Set<ScheduleResponseDto> workingSchedule;

    // Getters and setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<Long> getQualifiedOrderIds() {
        return qualifiedOrderIds;
    }

    public void setQualifiedOrderIds(Set<Long> qualifiedOrderIds) {
        this.qualifiedOrderIds = qualifiedOrderIds;
    }

    public Set<ScheduleResponseDto> getWorkingSchedule() {
        return workingSchedule;
    }

    public void setWorkingSchedule(Set<ScheduleResponseDto> workingSchedule) {
        this.workingSchedule = workingSchedule;
    }
}
