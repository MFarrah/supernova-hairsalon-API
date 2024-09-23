package nl.mfarr.supernova.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String description;

    private BigDecimal price;

    private int duration; // Duur van de behandeling in minuten

    @ManyToMany(mappedBy = "qualifiedOrders")
    private Set<EmployeeEntity> qualifiedEmployees; // Medewerkers die voor deze behandeling gekwalificeerd zijn

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<EmployeeEntity> getQualifiedEmployees() {
        return qualifiedEmployees;
    }

    public void setQualifiedEmployees(Set<EmployeeEntity> qualifiedEmployees) {
        this.qualifiedEmployees = qualifiedEmployees;
    }
}
