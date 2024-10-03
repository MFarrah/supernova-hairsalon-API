package nl.mfarr.supernova.repositories;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface RosterRepository extends JpaRepository<RosterEntity, Long> {
    List<RosterEntity> findByEmployeeAndMonth(EmployeeEntity employee, int month);

    List<RosterEntity> findByEmployeeAndDate(EmployeeEntity employee, LocalDate date);
}