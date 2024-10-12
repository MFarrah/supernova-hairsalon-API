package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.timeSlotDtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeSlotMapper {

    public TimeSlotResponseDto toDto(TimeSlotEntity timeSlotEntity) {
        TimeSlotResponseDto dto = new TimeSlotResponseDto();
        dto.setTimeSlotId(timeSlotEntity.getId());
        dto.setRosterId(timeSlotEntity.getRoster().getId());
        var timeslotId = timeSlotEntity.getBooking().getId();
        if (timeslotId != null) {
            dto.setBookingId(timeslotId);  // Map bookingId if available
        } else {
            dto.setBookingId(null);  // Set to null if no booking is associated
        }
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