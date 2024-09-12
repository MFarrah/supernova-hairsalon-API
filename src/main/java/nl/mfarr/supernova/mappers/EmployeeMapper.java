package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeEntity toEntity(EmployeeRequestDto employeeRequestDto) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmail(employeeRequestDto.getEmail());
        employeeEntity.setPassword(employeeRequestDto.getPassword());
        employeeEntity.setFirstName(employeeRequestDto.getFirstName());
        employeeEntity.setLastName(employeeRequestDto.getLastName());
        employeeEntity.setDateOfBirth(employeeRequestDto.getDateOfBirth());
        employeeEntity.setGender(employeeRequestDto.getGender());
        employeeEntity.setPhoneNumber(employeeRequestDto.getPhoneNumber());
        employeeEntity.setSkills(employeeRequestDto.getSkills());
        return employeeEntity;
    }

    public EmployeeResponseDto toResponseDto(EmployeeEntity employeeEntity) {
        EmployeeResponseDto responseDto = new EmployeeResponseDto();
        responseDto.setEmployeeId(employeeEntity.getEmployeeId());
        responseDto.setEmail(employeeEntity.getEmail());
        responseDto.setFirstName(employeeEntity.getFirstName());
        responseDto.setLastName(employeeEntity.getLastName());
        responseDto.setGender(employeeEntity.getGender());
        responseDto.setRoles(employeeEntity.getRoles());
        return responseDto;
    }
}
