package nl.mfarr.supernova.entities;

import jakarta.persistence.*;
import nl.mfarr.supernova.enums.Gender;
import nl.mfarr.supernova.enums.Role;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.EMPLOYEE; // Standaard EMPLOYEE

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phoneNumber;

    @ElementCollection
    private Set<String> skills; // Skills set om orders te matchen

    @OneToMany(mappedBy = "employee")
    private Set<BookingEntity> bookings;

    @OneToOne
    private ScheduleEntity availability;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }

    public Set<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(Set<BookingEntity> bookings) {
        this.bookings = bookings;
    }

    public ScheduleEntity getAvailability() {
        return availability;
    }

    public void setAvailability(ScheduleEntity availability) {
        this.availability = availability;
    }
}
