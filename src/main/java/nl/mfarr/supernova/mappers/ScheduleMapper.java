package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.ScheduleEntity;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public ScheduleEntity toEntity(ScheduleRequestDto scheduleRequestDto) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        // Assume employee entity is fetched from service layer
        scheduleEntity.setDayOfWeek(scheduleRequestDto.getDayOfWeek());
        scheduleEntity.setStartTime(scheduleRequestDto.getStartTime());
        scheduleEntity.setEndTime(scheduleRequestDto.getEndTime());
        return scheduleEntity;
    }

    public ScheduleResponseDto toResponseDto(ScheduleEntity scheduleEntity) {
        ScheduleResponseDto responseDto = new ScheduleResponseDto();
        responseDto.setScheduleId(scheduleEntity.getScheduleId());
        responseDto.setEmployeeId(scheduleEntity.getEmployee().getEmployeeId());
        responseDto.setDayOfWeek(scheduleEntity.getDayOfWeek());
        responseDto.setStartTime(scheduleEntity.getStartTime());
        responseDto.setEndTime(scheduleEntity.getEndTime());
        return responseDto;
    }
}
