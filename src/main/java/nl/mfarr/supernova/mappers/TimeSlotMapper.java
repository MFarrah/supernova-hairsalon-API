package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.stereotype.Component;

@Component
public class TimeSlotMapper {

    public static TimeSlotEntity toEntity(Object dto) {
        return GenericMapperHelper.mapToEntity(dto, TimeSlotEntity.class);
    }

    public static Object toDto(TimeSlotEntity entity, Class<?> dtoClass) {
        return GenericMapperHelper.mapToDto(entity, dtoClass);
    }
}
