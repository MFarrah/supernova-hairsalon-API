package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.models.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
