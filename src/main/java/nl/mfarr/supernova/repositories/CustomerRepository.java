package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);
}
