package nl.mfarr.supernova.entities;

import jakarta.persistence.*;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roster")
public class RosterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roster_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @Column(name = "month")
    private int month;

    @Column(name = "year")
    private int year;

    @OneToMany
    @JoinColumn(name = "roster_id")
    private List<TimeSlotEntity> timeSlots = new ArrayList<>();



    @Override
    public String toString() {
        return "RosterEntity{" +
                "employee=" + employee +
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

    public List<TimeSlotEntity> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlotEntity> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
