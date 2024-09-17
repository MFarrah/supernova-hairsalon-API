package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {

    // Aangepaste query om te controleren of een medewerker beschikbaar is
    @Query("SELECT CASE WHEN COUNT(ts) > 0 THEN true ELSE false END " +
            "FROM TimeSlotEntity ts " +
            "WHERE ts.employee.employeeId = :employeeId " +
            "AND ts.date = :date " +
            "AND ts.startTime <= :startTime " +
            "AND ts.endTime >= :endTime")
    boolean isEmployeeAvailable(@Param("employeeId") Long employeeId,
                                @Param("date") LocalDate date,
                                @Param("startTime") LocalTime startTime,
                                @Param("endTime") LocalTime endTime);

    // Zoek beschikbare tijdsloten voor een medewerker
    List<TimeSlotEntity> findByEmployeeEmployeeIdAndDate(Long employeeId, LocalDate date);

}
