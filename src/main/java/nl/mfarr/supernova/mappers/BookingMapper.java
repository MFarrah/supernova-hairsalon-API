package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.BookingCustomerRequestDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.helpers.GenericMapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import nl.mfarr.supernova.entities.OrderEntity;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    private final GenericMapperHelper genericMapperHelper;

    @Autowired
    public BookingMapper(GenericMapperHelper genericMapperHelper) {
        this.genericMapperHelper = genericMapperHelper;
    }

    public BookingEntity toEntity(BookingCustomerRequestDto dto) {
        BookingEntity entity = new BookingEntity();
        entity.setCustomerId(dto.getCustomerId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setDate(dto.getDate());
        entity.setStartTime(dto.getStartTime());
        entity.setNotes(dto.getNotes());
        entity.setStatus(BookingStatus.RESERVED);

        return entity;
    }

    public BookingCustomerRequestDto toDto(BookingEntity entity) {
        BookingCustomerRequestDto dto = new BookingCustomerRequestDto();
        genericMapperHelper.mapToDto(entity, dto);
        dto.setOrderIds(entity.getOrders().stream()
                .map(OrderEntity::getId)
                .collect(Collectors.toList()));
        return dto;
    }
}
