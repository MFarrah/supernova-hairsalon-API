// RosterMapper.java
package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.RosterResponseDto;
import nl.mfarr.supernova.dtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
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

    private RosterResponseDto.TimeSlotDto toDto(TimeSlotEntity timeSlotEntity) {
        RosterResponseDto.TimeSlotDto dto = new RosterResponseDto.TimeSlotDto();
        dto.setBookedId(timeSlotEntity.getBookedId());
        dto.setWeek(timeSlotEntity.getWeek());
        dto.setDate(timeSlotEntity.getDate());
        dto.setStartTime(timeSlotEntity.getStartTime());
        dto.setEndTime(timeSlotEntity.getEndTime());
        dto.setStatus(timeSlotEntity.getStatus());
        return dto;
    }


}