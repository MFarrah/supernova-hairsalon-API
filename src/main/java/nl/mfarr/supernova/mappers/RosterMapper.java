package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.RosterRequestDto;
import nl.mfarr.supernova.dtos.RosterResponseDto;
import nl.mfarr.supernova.dtos.TimeSlotRequestDto;
import nl.mfarr.supernova.dtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RosterMapper {

    public RosterEntity toEntity(RosterRequestDto dto, EmployeeEntity employee) {
        RosterEntity roster = new RosterEntity();
        roster.setEmployee(employee);
        roster.setMonth(dto.getMonth());
        roster.setYear(dto.getYear());
        roster.setTimeSlots(toTimeSlotEntities(dto.getTimeSlots(), roster));
        return roster;
    }

    public RosterResponseDto toDto(RosterEntity roster) {
        RosterResponseDto dto = new RosterResponseDto();
        dto.setId(roster.getId());
        dto.setEmployeeId(roster.getEmployee().getId());
        dto.setMonth(roster.getMonth());
        dto.setYear(roster.getYear());
        dto.setTimeSlots(toTimeSlotDtos(roster.getTimeSlots()));
        return dto;
    }

    public List<RosterEntity.TimeSlot> toTimeSlotEntities(List<TimeSlotRequestDto> dtos, RosterEntity roster) {
        return dtos.stream().map(dto -> {
            RosterEntity.TimeSlot timeSlot = new RosterEntity.TimeSlot();
            timeSlot.setDate(dto.getDate());
            timeSlot.setStartTime(dto.getStartTime());
            timeSlot.setEndTime(dto.getEndTime());
            timeSlot.setStatus(dto.getStatus());
            return timeSlot;
        }).collect(Collectors.toList());
    }

    public List<TimeSlotResponseDto> toTimeSlotDtos(List<RosterEntity.TimeSlot> timeSlots) {
        return timeSlots.stream().map(slot -> {
            TimeSlotResponseDto dto = new TimeSlotResponseDto();
            dto.setDate(slot.getDate());
            dto.setStartTime(slot.getStartTime());
            dto.setEndTime(slot.getEndTime());
            dto.setStatus(slot.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }
}