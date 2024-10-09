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

    @ElementCollection
    @CollectionTable(name = "time_slots", joinColumns = @JoinColumn(name = "roster_id"))
    private List<TimeSlot> timeSlots = new ArrayList<>();

    @Embeddable
    public static class TimeSlot {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "timeslot_id")
        private Long id;
        @Column(name = "booking_id")
        private Long bookingId;

        @Column(name = "week")
        private int week;

        @Column(name = "date")
        private LocalDate date;

        @Column(name = "start_time")
        private LocalTime startTime;

        @Column(name = "end_time")
        private LocalTime endTime;

        @Column(name = "timeslot_status")
        @Enumerated(EnumType.STRING)
        private TimeSlotStatus status;

        // Getters and Setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getBookingId() {
            return bookingId;
        }

        public void setBookingId(Long bookedId) {
            this.bookingId = bookedId;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalTime startTime) {
            this.startTime = startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalTime endTime) {
            this.endTime = endTime;
        }

        public TimeSlotStatus getStatus() {
            return status;
        }

        public void setStatus(TimeSlotStatus status) {
            this.status = status;
        }
    }

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

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
