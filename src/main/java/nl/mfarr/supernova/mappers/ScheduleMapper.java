package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.models.ScheduleEntity;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public ScheduleResponseDto toDto(ScheduleEntity scheduleEntity) {
        ScheduleResponseDto scheduleDto = new ScheduleResponseDto();
        scheduleDto.setScheduleId(scheduleEntity.getScheduleId());
        scheduleDto.setAvailability(scheduleEntity.getAvailability());
        scheduleDto.setWorkHours(scheduleEntity.getWorkHours());
        scheduleDto.setEmployeeId(scheduleEntity.getEmployee().getEmployeeId());
        return scheduleDto;
    }

    public ScheduleEntity toEntity(ScheduleRequestDto scheduleDto) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setAvailability(scheduleDto.getAvailability());
        scheduleEntity.setWorkHours(scheduleDto.getWorkHours());
        return scheduleEntity;
    }
}
