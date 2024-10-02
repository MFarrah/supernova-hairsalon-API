package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.TimeSlotResponseDto;
import nl.mfarr.supernova.mappers.TimeSlotMapper;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeSlotMapper timeSlotMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RosterService rosterRepository;

    public List<Object> getTimeSlotsByEmployeeIdAndDate(Long employeeId, LocalDate date) {
        return timeSlotRepository.findAllByEmployeeIdAndDate(employeeId, date)
                .stream()
                .map(entity -> timeSlotMapper.toDto(entity, TimeSlotResponseDto.class))
                .collect(Collectors.toList());
    }

    public void deleteTimeSlot(Long timeSlotId) {
        timeSlotRepository.deleteById(timeSlotId);
    }
}