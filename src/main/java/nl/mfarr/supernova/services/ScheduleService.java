package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.mappers.ScheduleMapper;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        ScheduleEntity scheduleEntity = scheduleMapper.toEntity(scheduleRequestDto);
        scheduleEntity = scheduleRepository.save(scheduleEntity);
        return scheduleMapper.toResponseDto(scheduleEntity);
    }

    public List<ScheduleResponseDto> getSchedulesByEmployeeAndDay(Long employeeId, DayOfWeek dayOfWeek) {
        return scheduleRepository.findByEmployeeEmployeeIdAndDayOfWeek(employeeId, dayOfWeek)
                .stream()
                .map(scheduleMapper::toResponseDto)
                .toList();
    }
}
