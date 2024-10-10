package nl.mfarr.supernova.dtos;

public class TimeSlotRequestDto {

        private Long rosterId;
        private Long bookingId;
        private Integer week;
        private String date;
        private String startTime;
        private String endTime;
        private String status;

        // Getters and setters

        public Long getRosterId() {
            return rosterId;
        }

        public void setRosterId(Long rosterId) {
            this.rosterId = rosterId;
        }

        public Long getBookingId() {
            return bookingId;
        }

        public void setBookingId(Long bookingId) {
            this.bookingId = bookingId;
        }

        public Integer getWeek() {
            return week;
        }

        public void setWeek(Integer week) {
            this.week = week;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
}
