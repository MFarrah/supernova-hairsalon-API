package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {
    @Query("SELECT t.week FROM TimeSlotEntity t WHERE t.roster.id = :rosterId")
    Integer findWeekByRosterId(@Param("rosterId") Long rosterId);
    List<TimeSlotEntity> findByBookingId(Long bookingId);

    List<TimeSlotEntity> findByRosterId(Long rosterId);
}
