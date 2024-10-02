package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {

    List<TimeSlotEntity> findAllByEmployeeIdAndDate(Long employeeId, LocalDate date);
}
