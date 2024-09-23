package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> findByEmployeeEmployeeId(Long employeeId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
            "FROM ScheduleEntity s WHERE s.employee.employeeId = :employeeId " +
            "AND s.dayOfWeek = :dayOfWeek " +
            "AND s.startTime <= :startTime AND s.endTime >= :endTime")
    boolean isEmployeeAvailable(@Param("employeeId") Long employeeId,
                                @Param("dayOfWeek") DayOfWeek dayOfWeek,
                                @Param("startTime") LocalTime startTime,
                                @Param("endTime") LocalTime endTime);
}
