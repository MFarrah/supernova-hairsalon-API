package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.enums.Gender;

import java.util.stream.Collectors;

public class EmployeeMapper {

    public static EmployeeEntity toEntity(EmployeeRequestDto dto) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setSkills(dto.getSkills());
        return entity;
    }

    public static EmployeeResponseDto toResponseDto(EmployeeEntity entity) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setGender(entity.getGender().name());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setSkills(entity.getSkills());
        dto.setBookingIds(entity.getBookings().stream().map(BookingEntity::getBookingId).collect(Collectors.toSet()));
        dto.setAvailabilityId(entity.getAvailability().getScheduleId());
        return dto;
    }
}