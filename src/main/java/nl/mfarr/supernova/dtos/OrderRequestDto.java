package nl.mfarr.supernova.dtos;

import java.time.Duration;

public class OrderRequestDto {
    private String description;
    private double price;
    private Duration estimatedTime; // Duration in minutes

    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Duration getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Duration estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}
