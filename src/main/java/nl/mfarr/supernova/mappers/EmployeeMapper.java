package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;

public class EmployeeMapper {

    public static EmployeeEntity toEntity(EmployeeRequestDto dto) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setEmail(dto.getEmail());
        employee.setPassword(dto.getPassword());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setGender(dto.getGender());
        employee.setPhoneNumber(dto.getPhoneNumber());
        return employee;
    }

    public static EmployeeResponseDto toResponseDto(EmployeeEntity employee) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setEmail(employee.getEmail());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        return dto;
    }
}
