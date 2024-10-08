package nl.mfarr.supernova.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nl.mfarr.supernova.helpers.DurationJsonHelper;

import java.math.BigDecimal;
import java.time.Duration;

public class OrderUpsertRequestDto {
private Long id;
    private String description;
    private BigDecimal price;
    @JsonDeserialize(using = DurationJsonHelper.DurationDeserializer.class)
    private Duration duration;

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
