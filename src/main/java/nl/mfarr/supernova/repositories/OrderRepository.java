package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    // Zoek naar diensten (orders) op basis van hun beschrijving
    OrderEntity findByDescription(String description);
}
