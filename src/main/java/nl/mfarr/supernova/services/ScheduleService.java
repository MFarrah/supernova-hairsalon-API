package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.mappers.ScheduleMapper;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));
        ScheduleEntity entity = ScheduleMapper.toEntity(dto, employee);
        ScheduleEntity savedEntity = scheduleRepository.save(entity);
        return ScheduleMapper.toResponseDto(savedEntity);
    }

    public ScheduleResponseDto getScheduleById(Long id) {
        ScheduleEntity entity = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));
        return ScheduleMapper.toResponseDto(entity);
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(ScheduleMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto) {
        ScheduleEntity entity = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));
        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));
        entity.setEmployee(employee);
        entity.setDayOfWeek(dto.getDayOfWeek());
        entity.setStartTime(dto.getStartTime());
        entity.setEndTime(dto.getEndTime());
        ScheduleEntity updatedEntity = scheduleRepository.save(entity);
        return ScheduleMapper.toResponseDto(updatedEntity);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}