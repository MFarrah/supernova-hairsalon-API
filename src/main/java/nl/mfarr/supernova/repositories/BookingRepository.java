package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    // Zoek naar boekingen op basis van klant-ID en status
    List<BookingEntity> findByCustomerCustomerIdAndStatus(Long customerId, BookingStatus status);

    // Zoek naar boekingen op basis van medewerker-ID en status
    List<BookingEntity> findByEmployeeEmployeeIdAndStatus(Long employeeId, BookingStatus status);

    // Zoek naar boekingen tussen twee datums (gebruik bookingDate in plaats van date)
    List<BookingEntity> findByBookingDateBetween(LocalDate startDate, LocalDate endDate);
}
