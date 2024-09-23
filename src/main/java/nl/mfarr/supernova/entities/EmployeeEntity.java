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

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employee_roles")
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "employee_qualifications",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private Set<OrderEntity> qualifiedOrders; // Employee qualifications for orders

    @OneToMany(mappedBy = "employee")
    private Set<ScheduleEntity> schedules; // Employee availability

    @OneToMany(mappedBy = "employee")
    private Set<TimeSlotEntity> timeSlots; // Time slots assigned for bookings

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<OrderEntity> getQualifiedOrders() {
        return qualifiedOrders;
    }

    public void setQualifiedOrders(Set<OrderEntity> qualifiedOrders) {
        this.qualifiedOrders = qualifiedOrders;
    }

    public Set<ScheduleEntity> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<ScheduleEntity> schedules) {
        this.schedules = schedules;
    }

    public Set<TimeSlotEntity> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Set<TimeSlotEntity> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
