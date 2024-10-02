package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RosterRepository extends JpaRepository<RosterEntity, Long> {
    List<RosterEntity> findByEmployee(EmployeeEntity employee);
    Optional<RosterEntity> findByEmployeeAndMonthAndYear(EmployeeEntity employee, int month, int year);
    boolean existsByEmployeeAndDate(EmployeeEntity employee, LocalDate date);

    List<RosterEntity> findByDate(LocalDate date);
}