package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    Optional<AdminEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
