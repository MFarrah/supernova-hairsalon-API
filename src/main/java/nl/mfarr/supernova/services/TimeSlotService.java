package nl.mfarr.supernova.services;

import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    // Controleer of een medewerker beschikbaar is
    public boolean isEmployeeAvailable(Long employeeId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        return timeSlotRepository.isEmployeeAvailable(employeeId, date, startTime, endTime);
    }

    // Haal beschikbare tijdsloten op voor een medewerker op een specifieke datum
    public List<TimeSlotEntity> getAvailableTimeSlotsForEmployee(Long employeeId, LocalDate date) {
        return timeSlotRepository.findByEmployeeEmployeeIdAndDate(employeeId, date);
    }

    // Maak een nieuw tijdslot voor een medewerker
    public TimeSlotEntity createTimeSlot(TimeSlotEntity timeSlotEntity) {
        return timeSlotRepository.save(timeSlotEntity);
    }
}
