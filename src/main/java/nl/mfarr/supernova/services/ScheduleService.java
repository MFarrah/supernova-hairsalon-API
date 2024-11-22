package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.mappers.ScheduleMapper;
import nl.mfarr.supernova.models.ScheduleEntity;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ScheduleResponseDto> getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .map(scheduleMapper::toDto);
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleDto) {
        ScheduleEntity scheduleEntity = scheduleMapper.toEntity(scheduleDto);
        ScheduleEntity savedEntity = scheduleRepository.save(scheduleEntity);
        return scheduleMapper.toDto(savedEntity);
    }

    public ScheduleResponseDto updateSchedule(ScheduleRequestDto scheduleDto) {
        ScheduleEntity scheduleEntity = scheduleMapper.toEntity(scheduleDto);
        ScheduleEntity updatedEntity = scheduleRepository.save(scheduleEntity);
        return scheduleMapper.toDto(updatedEntity);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}
