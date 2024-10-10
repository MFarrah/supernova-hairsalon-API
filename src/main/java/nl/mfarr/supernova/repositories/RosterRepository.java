// RosterRepository.java
package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface RosterRepository extends JpaRepository<RosterEntity, Long> {

    List<RosterEntity> findByEmployeeIdAndMonthAndYear(Long id, int monthValue, int year);

    boolean existsByEmployeeAndYearAndMonth(EmployeeEntity employee, int year, int month);
}