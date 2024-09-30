package nl.mfarr.supernova.repositories;

import nl.mfarr.supernova.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

}
