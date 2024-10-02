package nl.mfarr.supernova.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class RosterResponseDto {

        private Long id;

        private EmployeeResponseDto employee;

        private LocalDate date;

        private LocalTime startTime;

        private LocalTime endTime;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public EmployeeResponseDto getEmployee() {
            return employee;
        }

        public void setEmployee(EmployeeResponseDto employee) {
            this.employee = employee;
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
}
