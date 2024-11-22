package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    List<BookingEntity> findByCustomerCustomerIdAndStatus(Long customerId, BookingStatus status);

    List<BookingEntity> findByEmployeeEmployeeIdAndStatus(Long employeeId, BookingStatus status);

    List<BookingEntity> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
