package nl.mfarr.supernova.entities;

import jakarta.persistence.*;
import nl.mfarr.supernova.enums.Gender;
import nl.mfarr.supernova.enums.Role;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "employee_entity")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(unique = true, name = "email")  // Toegevoegd voor unieke e-mail
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;


    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ElementCollection(fetch = FetchType.LAZY)  // Overwogen LAZY laden voor optimalisatie
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employee_roles")
    private Set<Role> roles;


    @ElementCollection
    @CollectionTable(name = "employee_qualified_orders", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "qualified_order_id")
    private Set<Long> qualifiedOrderIds;


    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "working_schedule")
    private Set<ScheduleEntity> workingSchedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Long> getQualifiedOrderIds() {
        return qualifiedOrderIds;
    }

    public void setQualifiedOrderIds(Set<Long> qualifiedOrderIds) {
        this.qualifiedOrderIds = qualifiedOrderIds;
    }

    public Set<ScheduleEntity> getWorkingSchedule() {
        return workingSchedule;
    }

    public void setWorkingSchedule(Set<ScheduleEntity> workingSchedule) {
        this.workingSchedule = workingSchedule;
    }
}
