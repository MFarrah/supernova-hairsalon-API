package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {

    @Query("SELECT t FROM TimeSlotEntity t WHERE t.employee.employeeId = :employeeId " +
            "AND t.date = :date AND t.startTime >= :startTime AND t.endTime <= :endTime")
    List<TimeSlotEntity> findAvailableSlotsForEmployee(@Param("employeeId") Long employeeId,
                                                       @Param("date") LocalDate date,
                                                       @Param("startTime") LocalTime startTime,
                                                       @Param("endTime") LocalTime endTime);
}
