package nl.mfarr.supernova.dtos;

import nl.mfarr.supernova.enums.Gender;
import nl.mfarr.supernova.enums.Role;

import java.time.LocalDate;
import java.util.Set;

public class EmployeeUpdateRequestDto {

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private Gender gender;

    private Set<Role> roles;
    private Set<Long> qualifiedOrderIds;
    private Set<ScheduleCreateRequestDto> workingSchedule;

    // Getters and Setters
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    public Set<ScheduleCreateRequestDto> getWorkingSchedule() {
        return workingSchedule;
    }

    public void setWorkingSchedule(Set<ScheduleCreateRequestDto> workingSchedule) {
        this.workingSchedule = workingSchedule;
    }
}
