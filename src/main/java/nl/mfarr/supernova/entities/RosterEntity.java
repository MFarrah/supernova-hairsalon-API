package nl.mfarr.supernova.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.TreeSet;

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

    @Column(name = "month")
    private int month;

    @Column(name = "year")
    private int year;

    @ElementCollection
    @CollectionTable(name = "roster_time_slots", joinColumns = @JoinColumn(name = "roster_id"))
    @Column(name = "time_slot")
    private Set<LocalTime> timeSlots = new TreeSet<>();

    @Override
    public String toString() {
        return "RosterEntity{" +
                "employee=" + employee +
                ", date=" + date +
                ", month=" + month +
                ", year=" + year +
                ", timeSlots=" + timeSlots +
                '}';
    }

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

    public Set<LocalTime> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Set<LocalTime> timeSlots) {
        this.timeSlots = timeSlots;
    }
}