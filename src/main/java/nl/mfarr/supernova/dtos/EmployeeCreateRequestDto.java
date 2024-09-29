package nl.mfarr.supernova.dtos;

import nl.mfarr.supernova.enums.Gender;

import java.time.LocalDate;
import java.util.Set;

public class EmployeeCreateRequestDto {

    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private Gender gender;
    private Set<Long> qualifiedOrderIds;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
}
