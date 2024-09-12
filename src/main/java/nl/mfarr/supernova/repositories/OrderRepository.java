package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findById(Long id);
    // Zoek naar diensten (orders) op basis van hun beschrijving
    OrderEntity findByDescription(String description);
}
