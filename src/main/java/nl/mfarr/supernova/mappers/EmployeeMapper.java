package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.employeeDtos.EmployeeUpsertRequestDto;
import nl.mfarr.supernova.dtos.employeeDtos.EmployeeResponseDto;
import nl.mfarr.supernova.dtos.scheduleDtos.ScheduleUpsertRequestDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private OrderRepository orderRepository;

    public EmployeeEntity toEntity(EmployeeUpsertRequestDto dto) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setPassword(dto.getPassword());
        employee.setGender(dto.getGender());
        employee.setRoles(dto.getRoles() == null ? Collections.emptySet() : dto.getRoles());

        Set<Long> orderIds = dto.getQualifiedOrderIds();
        if (orderIds != null) {
            employee.setQualifiedOrders(orderIds.stream()
                    .map(orderRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet()));
        } else {
            employee.setQualifiedOrders(Collections.emptySet());
        }

// Map workingSchedule using ScheduleMapper
        Set<ScheduleUpsertRequestDto> workingSchedule = dto.getWorkingSchedule();
        if (workingSchedule != null) {
            Set<ScheduleEntity> schedules = workingSchedule.stream()
                    .map(scheduleDto -> scheduleMapper.toEntity(scheduleDto, employee))
                    .collect(Collectors.toSet());
            employee.setWorkingSchedule(schedules);
        } else {
            employee.setWorkingSchedule(Collections.emptySet());
        }

        return employee;
    }

    public EmployeeResponseDto toDto(EmployeeEntity employee) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        if (employee.getProfileImageUrl() != null) {
            dto.setProfileImageUrl(employee.getProfileImageUrl());
        }
        dto.setEmployeeId(employee.getId());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDateOfBirth(employee.getDateOfBirth());
        dto.setEmail(employee.getEmail());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setGender(employee.getGender());
        dto.setRoles(employee.getRoles());


        // Map qualifiedOrders to DTO
        dto.setQualifiedOrderIds(employee.getQualifiedOrders() == null ?
                Collections.emptySet() :
                employee.getQualifiedOrders().stream()
                        .map(order -> order.getId())
                        .collect(Collectors.toSet()));

// Map workingSchedule using ScheduleMapper
        dto.setWorkingSchedule(employee.getWorkingSchedule() == null ?
                Collections.emptySet() :
                employee.getWorkingSchedule().stream()
                        .map(scheduleMapper::toDto)
                        .collect(Collectors.toSet()));

        return dto;
    }

    public void updateEntityFromDto(EmployeeUpsertRequestDto dto, EmployeeEntity employee) {
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setPassword(dto.getPassword());
        employee.setGender(dto.getGender());
        employee.setRoles(dto.getRoles());

        // Zet de qualified orders
        Set<Long> orderIds = dto.getQualifiedOrderIds();
        employee.setQualifiedOrders(orderIds.stream()
                .map(orderRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet()));

        // Update workingSchedule
        Set<ScheduleEntity> schedules = dto.getWorkingSchedule().stream()
                .map(scheduleDto -> scheduleMapper.toEntity(scheduleDto, employee))
                .collect(Collectors.toSet());
        employee.setWorkingSchedule(schedules);
    }


    public List<EmployeeResponseDto> toDtoList(List<EmployeeEntity> employees) {
        return employees.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


}
