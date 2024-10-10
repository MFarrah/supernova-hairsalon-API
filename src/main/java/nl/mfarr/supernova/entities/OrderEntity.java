package nl.mfarr.supernova.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "order_entity")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
@Column(name = "description")
    private String description;

    @Positive(message = "Price must be positive")
    @Column(name = "price")
    private BigDecimal price;
@Column(name = "duration")
    private int duration;

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

    public @Positive(message = "Price must be positive") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@Positive(message = "Price must be positive") BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
