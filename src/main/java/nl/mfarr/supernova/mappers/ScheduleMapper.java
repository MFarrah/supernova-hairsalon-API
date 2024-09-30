package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.ScheduleCreateRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.dtos.ScheduleUpdateRequestDto;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.helpers.GenericMapperHelper;

public class ScheduleMapper {
    public static ScheduleEntity toEntity(ScheduleCreateRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, ScheduleEntity.class);
    }

    public static ScheduleEntity toEntity(ScheduleUpdateRequestDto dto) {
        return GenericMapperHelper.mapToEntity(dto, ScheduleEntity.class);
    }

    public ScheduleResponseDto toDto(ScheduleEntity entity) {
        return GenericMapperHelper.mapToDto(entity, ScheduleResponseDto.class);
    }
}
