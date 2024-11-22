package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.models.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

}
