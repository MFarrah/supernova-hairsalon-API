package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

    List<ScheduleEntity> findByEmployeeEmployeeIdAndDayOfWeek(Long employeeId, DayOfWeek dayOfWeek);
}
