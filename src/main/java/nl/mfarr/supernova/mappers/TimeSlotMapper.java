package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeSlotMapper {

    public TimeSlotResponseDto toDto(TimeSlotEntity timeSlotEntity) {
        TimeSlotResponseDto dto = new TimeSlotResponseDto();
        dto.setTimeSlotId(timeSlotEntity.getId());
        dto.setRosterId(timeSlotEntity.getRoster().getId()); // Zorg ervoor dat de relatie correct is
        dto.setBookingId(timeSlotEntity.getBookingId() != null ? timeSlotEntity.getBookingId() : null);
        dto.setWeek(timeSlotEntity.getWeek());
        dto.setDate(timeSlotEntity.getDate());
        dto.setStartTime(timeSlotEntity.getStartTime());
        dto.setEndTime(timeSlotEntity.getEndTime());
        dto.setStatus(timeSlotEntity.getStatus());
        return dto;
    }

    public List<TimeSlotResponseDto> toDtoList(List<TimeSlotEntity> availableSlots) {
        return availableSlots.stream()
                .map(this::toDto)
                .toList();
    }
}
