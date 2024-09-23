package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.mappers.ScheduleMapper;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        ScheduleEntity schedule = scheduleMapper.toEntity(requestDto);
        scheduleRepository.save(schedule);
        return scheduleMapper.toResponseDto(schedule);
    }

    public List<ScheduleResponseDto> getSchedulesForEmployee(Long employeeId) {
        return scheduleRepository.findByEmployeeEmployeeId(employeeId).stream()
                .map(scheduleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public boolean isEmployeeAvailable(Long employeeId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return scheduleRepository.isEmployeeAvailable(employeeId, dayOfWeek, startTime, endTime);
    }
    // Verwijder een rooster
    public void deleteSchedule(Long scheduleId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Rooster niet gevonden"));
        scheduleRepository.delete(schedule);
    }

    // Controleer of een medewerker beschikbaar is op een bepaalde dag en tijd
    public boolean isEmployeeAvailable(Long employeeId, String dayOfWeek, String startTime, String endTime) {
        return scheduleRepository.isEmployeeAvailable(employeeId, DayOfWeek.valueOf(dayOfWeek.toUpperCase()),
                LocalTime.parse(startTime), LocalTime.parse(endTime));
    }

}
