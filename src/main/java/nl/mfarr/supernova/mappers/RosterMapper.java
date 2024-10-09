// RosterMapper.java
package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.RosterResponseDto;
import nl.mfarr.supernova.entities.RosterEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RosterMapper {



    public RosterResponseDto toDto(RosterEntity rosterEntity) {
        RosterResponseDto dto = new RosterResponseDto();
        dto.setId(rosterEntity.getId());
        dto.setEmployeeId(rosterEntity.getEmployee().getId());
        dto.setMonth(rosterEntity.getMonth());
        dto.setYear(rosterEntity.getYear());
        dto.setTimeSlots(rosterEntity.getTimeSlots().stream().map(this::toDto).collect(Collectors.toList()));
        return dto;
    }

    private RosterResponseDto.TimeSlotDto toDto(RosterEntity.TimeSlot timeSlot) {
        RosterResponseDto.TimeSlotDto dto = new RosterResponseDto.TimeSlotDto();
        dto.setBookedId(timeSlot.getBookingId());
        dto.setWeek(timeSlot.getWeek());
        dto.setDate(timeSlot.getDate());
        dto.setStartTime(timeSlot.getStartTime());
        dto.setEndTime(timeSlot.getEndTime());
        dto.setStatus(timeSlot.getStatus());
        return dto;
    }


}