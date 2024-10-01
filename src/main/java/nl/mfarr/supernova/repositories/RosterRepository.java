package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RosterRepository extends JpaRepository<RosterEntity, Long> {
    List<RosterEntity> findByEmployee(EmployeeEntity employee);

    boolean existsByEmployeeAndDate(EmployeeEntity employee, LocalDate date);

    List<RosterEntity> findByDate(LocalDate date);
}