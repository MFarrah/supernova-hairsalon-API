package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.models.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

}

