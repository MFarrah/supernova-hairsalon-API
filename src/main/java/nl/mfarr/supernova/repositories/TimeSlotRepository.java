package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {


    List<TimeSlotEntity> findByWeekAndEmployee(int week, EmployeeEntity employee);

    List<TimeSlotEntity> findByDateAndEmployee(LocalDate date, EmployeeEntity employee);


    List<TimeSlotEntity> findByRosterAndDate(RosterEntity roster, LocalDate date);

    List<TimeSlotEntity> findByEmployeeAndDate(EmployeeEntity employee, LocalDate date);

    List<TimeSlotEntity> findByEmployeeAndDateBetween(EmployeeEntity employee, LocalDate localDate, LocalDate localDate1);

    List<TimeSlotEntity> findByStatus(TimeSlotStatus timeSlotStatus);

    List<TimeSlotEntity> findByDateAfter(LocalDate fromDate);

    Collection<Object> findByRoster(RosterEntity rosterEntity);
}