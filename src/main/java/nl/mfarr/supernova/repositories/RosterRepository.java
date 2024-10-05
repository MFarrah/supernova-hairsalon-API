// RosterRepository.java
package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RosterRepository extends JpaRepository<RosterEntity, Long> {
    List<RosterEntity> findByEmployeeAndMonthAndYear(EmployeeEntity employee, int month, int year);

    @Query("SELECT r FROM RosterEntity r JOIN r.timeSlots t WHERE r.employee = :employee AND r.year = :year AND t.week = :week")
    List<RosterEntity> findByEmployeeAndWeekAndYear(@Param("employee") EmployeeEntity employee, @Param("week") int week, @Param("year") int year);

    @Query("SELECT r FROM RosterEntity r JOIN r.timeSlots t WHERE r.employee = :employee AND t.date = :date")
    List<RosterEntity> findByEmployeeAndDate(@Param("employee") EmployeeEntity employee, @Param("date") LocalDate date);
}