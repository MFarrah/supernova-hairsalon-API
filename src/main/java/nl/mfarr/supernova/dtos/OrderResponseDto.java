package nl.mfarr.supernova.dtos;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Duration;

public class OrderResponseDto {

    private Long id;
    private String description;
    private BigDecimal price;
    private Duration duration;

    public OrderResponseDto(Long id, String description, BigDecimal price, Duration duration) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
