package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.stereotype.Component;

@Component
public class ScheduleMapper {

    public static ScheduleEntity toEntity(ScheduleRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, ScheduleEntity.class);
    }

    public ScheduleResponseDto toDto(ScheduleEntity entity) {
        return GenericMapperHelper.mapToDto(entity, ScheduleResponseDto.class);
    }
}
