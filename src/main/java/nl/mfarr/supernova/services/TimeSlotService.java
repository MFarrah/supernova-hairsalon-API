package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.TimeSlotRequestDto;
import nl.mfarr.supernova.dtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import nl.mfarr.supernova.mappers.TimeSlotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeSlotMapper timeSlotMapper;

    public List<TimeSlotResponseDto> getAvailableTimeSlots(Long employeeId, LocalDate date, LocalTime startTime, int duration) {
        LocalTime endTime = startTime.plusMinutes(duration);
        List<TimeSlotEntity> availableSlots = timeSlotRepository.findAvailableSlotsForEmployee(employeeId, date, startTime, endTime);
        return availableSlots.stream()
                .map(timeSlotMapper::toDto)
                .collect(Collectors.toList());
    }


    public boolean isEmployeeAvailable(Long employeeId, LocalDate date, LocalTime startTime, int duration) {
        List<TimeSlotEntity> availableSlots = timeSlotRepository.findAvailableSlotsForEmployee(employeeId, date, startTime, startTime.plusMinutes(duration));
        return !availableSlots.isEmpty();
    }

    public List<TimeSlotEntity> allocateTimeSlots(Long employeeId, LocalDate date, LocalTime startTime, int duration) {
        LocalTime endTime = startTime.plusMinutes(duration);
        return timeSlotRepository.findAvailableSlotsForEmployee(employeeId, date, startTime, endTime);
    }

    // Maak een nieuw tijdslot aan
    public TimeSlotResponseDto createTimeSlot(TimeSlotRequestDto requestDto) {
        TimeSlotEntity timeSlot = timeSlotMapper.toEntity(requestDto);
        timeSlotRepository.save(timeSlot);
        return timeSlotMapper.toDto(timeSlot);
    }

    // Verwijder een tijdslot
    public void deleteTimeSlot(Long timeSlotId) {
        TimeSlotEntity timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new RuntimeException("Tijdslot niet gevonden"));
        timeSlotRepository.delete(timeSlot);
    }
}