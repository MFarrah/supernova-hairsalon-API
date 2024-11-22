package nl.mfarr.supernova.dtos;

import java.math.BigDecimal;

public class OrderRequestDto {
    private String treatment;
    private BigDecimal price;
    private int duration;
    private Long bookingId;

    public OrderRequestDto() {
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
