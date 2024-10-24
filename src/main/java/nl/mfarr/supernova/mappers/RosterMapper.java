package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.rosterDtos.RosterResponseDto;
import nl.mfarr.supernova.dtos.timeSlotDtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.repositories.RosterRepository;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RosterMapper {

    @Autowired
    private RosterRepository rosterRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private TimeSlotMapper timeSlotMapper;

    public RosterResponseDto toDto(RosterEntity rosterEntity) {
        // Stap 1: Sla de RosterEntity op als deze geen ID heeft
        if (rosterEntity.getId() == null) {
            rosterEntity = rosterRepository.save(rosterEntity); // ID wordt gegenereerd
        }

        // Stap 2: Maak de RosterResponseDto aan en stel de basisvelden in
        RosterResponseDto rosterDto = new RosterResponseDto();
        rosterDto.setId(rosterEntity.getId());
        rosterDto.setEmployeeId(rosterEntity.getEmployee().getId());
        rosterDto.setMonth(rosterEntity.getMonth());
        rosterDto.setYear(rosterEntity.getYear());

        // Stap 3: Gebruik de timeSlotMapper om timeSlots om te zetten
        List<TimeSlotResponseDto> timeSlotDtos = rosterEntity.getTimeSlots().stream()
                .map(timeSlotMapper::toDto)
                .collect(Collectors.toList());
        rosterDto.setTimeSlots(timeSlotDtos);

        return rosterDto;
    }

    public RosterResponseDto toResponseDto(RosterEntity rosterEntity) {
        RosterResponseDto dto = new RosterResponseDto();
        dto.setId(rosterEntity.getId());
        dto.setEmployeeId(rosterEntity.getEmployee().getId());
        dto.setMonth(rosterEntity.getMonth());
        dto.setYear(rosterEntity.getYear());
        dto.setTimeSlots(rosterEntity.getTimeSlots().stream()
                .map(timeSlot -> {
                    // Map TimeSlotEntity to TimeSlotResponseDto
                    // Assuming you have a method for this in your mapper
                    return new TimeSlotResponseDto(timeSlot.getId(), timeSlot.getDate(), timeSlot.getStartTime(), timeSlot.getEndTime(), timeSlot.getStatus());
                })
                .collect(Collectors.toList()));
        return dto;
    }
}
