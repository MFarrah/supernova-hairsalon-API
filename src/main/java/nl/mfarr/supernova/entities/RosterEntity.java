package nl.mfarr.supernova.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "roster")
public class RosterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @Column(name = "date")
    private LocalDate date;

    @ElementCollection
    @CollectionTable(name = "roster_time_slots", joinColumns = @JoinColumn(name = "roster_id"))
    @Column(name = "time_slot")
    private Set<LocalTime> timeSlots;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<LocalTime> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Set<LocalTime> timeSlots) {
        this.timeSlots = timeSlots;
    }
}

