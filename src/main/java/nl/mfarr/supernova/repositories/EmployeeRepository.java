package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    static boolean existsByEmail(String email) { return false;
    }

    Optional<EmployeeEntity> findByEmail(String email);

}