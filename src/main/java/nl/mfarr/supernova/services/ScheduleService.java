package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.mappers.ScheduleMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto dto) {
        EmployeeEntity employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        ScheduleEntity schedule = ScheduleMapper.toEntity(dto, employee);
        ScheduleEntity savedSchedule = scheduleRepository.save(schedule);
        return ScheduleMapper.toResponseDto(savedSchedule);
    }
}
