package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByDescription(String description);
    Optional <OrderEntity> findById(Long id);
}
