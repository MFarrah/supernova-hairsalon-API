package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.OrderEntity;
import nl.mfarr.supernova.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    @Autowired
    private OrderRepository orderRepository;

    public EmployeeEntity toEntity(EmployeeRequestDto dto) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setPassword(dto.getPassword());
        entity.setGender(dto.getGender());
        // Qualified orders will be set in the service layer
        return entity;
    }

    public EmployeeResponseDto toResponseDto(EmployeeEntity entity) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        OrderEntity orderEntity = new OrderEntity();
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setGender(entity.getGender());
        dto.setRoles(entity.getRoles());
        // Convert qualified orders to IDs in the response DTO
        dto.setQualifiedOrderIds(
                entity.getQualifiedOrders().stream()
                        .map(OrderEntity::getOrderId)
                        .collect(java.util.stream.Collectors.toSet())
        );
        return dto;
    }
}
