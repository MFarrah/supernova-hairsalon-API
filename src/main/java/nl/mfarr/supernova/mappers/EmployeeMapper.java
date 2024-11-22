package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeEntity toEntity(EmployeeRequestDto dto) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setPassword(dto.getPassword());  // Mapping voor wachtwoord
        employee.setGender(dto.getGender());
        employee.setRoles(dto.getRoles());
        employee.setQualified(dto.getQualified());
        employee.setSchedules(dto.getSchedules());
        return employee;
    }

    public EmployeeResponseDto toResponseDto(EmployeeEntity employee) {
        EmployeeResponseDto response = new EmployeeResponseDto();
        response.setEmployeeId(employee.getEmployeeId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setDateOfBirth(employee.getDateOfBirth());
        response.setEmail(employee.getEmail());
        response.setPhoneNumber(employee.getPhoneNumber());
        response.setGender(employee.getGender());
        response.setRoles(employee.getRoles());
        response.setQualified(employee.getQualified());
        response.setSchedules(employee.getSchedules());
        return response;
    }
}
