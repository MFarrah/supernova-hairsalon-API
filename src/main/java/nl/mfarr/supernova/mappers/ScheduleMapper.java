package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.models.ScheduleEntity;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    @Autowired
    private EmployeeRepository employeeRepository;

    public ScheduleResponseDto toDto(ScheduleEntity scheduleEntity) {
        ScheduleResponseDto scheduleDto = new ScheduleResponseDto();
        scheduleDto.setScheduleId(scheduleEntity.getScheduleId());
        scheduleDto.setAvailabilityDate(scheduleEntity.getAvailabilityDate());
        scheduleDto.setStartTime(scheduleEntity.getStartTime());
        scheduleDto.setEndTime(scheduleEntity.getEndTime());
        scheduleDto.setEmployeeId(scheduleEntity.getEmployee().getEmployeeId());
        return scheduleDto;
    }

    public ScheduleEntity toEntity(ScheduleRequestDto scheduleDto) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setAvailabilityDate(scheduleDto.getAvailabilityDate());
        scheduleEntity.setStartTime(scheduleDto.getStartTime());
        scheduleEntity.setEndTime(scheduleDto.getEndTime());
        scheduleEntity.setEmployee(employeeRepository.findById(scheduleDto.getEmployeeId()).orElse(null));
        return scheduleEntity;
    }
}
