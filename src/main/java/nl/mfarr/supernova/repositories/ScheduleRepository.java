package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.DayOfWeek;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    // Zoek naar het rooster van een specifieke medewerker voor een bepaalde dag
    List<ScheduleEntity> findByEmployeeEmployeeIdAndDayOfWeek(Long employeeId, DayOfWeek dayOfWeek);
}
