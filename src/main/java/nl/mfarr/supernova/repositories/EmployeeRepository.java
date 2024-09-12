package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Optional<EmployeeEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    // Zoek naar medewerkers die beschikbaar zijn voor een bepaalde dag en tijd (op basis van hun rooster)
    List<EmployeeEntity> findByAvailabilityDayOfWeekAndAvailabilityStartTimeBeforeAndAvailabilityEndTimeAfter(
            DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime);
}
