package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {




    List<TimeSlotEntity> findByWeekAndEmployee(int week, EmployeeEntity employee);
}