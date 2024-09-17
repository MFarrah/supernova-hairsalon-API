package nl.mfarr.supernova.dtos;

import java.math.BigDecimal;

public class OrderResponseDto {

    private Long orderId;

    private String description;

    private BigDecimal price;  // BigDecimal voor prijs

    private int estimatedDurationInMinutes;  // Geschatte duur in minuten

    // Getters en Setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getEstimatedDurationInMinutes() {
        return estimatedDurationInMinutes;
    }

    public void setEstimatedDurationInMinutes(int estimatedDurationInMinutes) {
        this.estimatedDurationInMinutes = estimatedDurationInMinutes;
    }
}
