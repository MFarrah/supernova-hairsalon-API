package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.models.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

}
