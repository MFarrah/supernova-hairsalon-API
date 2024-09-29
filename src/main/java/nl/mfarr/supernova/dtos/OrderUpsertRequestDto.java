package nl.mfarr.supernova.dtos;

import java.math.BigDecimal;

public class OrderUpsertRequestDto {

    private String description;
    private BigDecimal price;
    private int duration;

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
