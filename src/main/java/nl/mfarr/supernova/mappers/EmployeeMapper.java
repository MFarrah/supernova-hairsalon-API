package nl.mfarr.supernova.mappers;

import nl.mfarr.supernova.dtos.EmployeeRequestDto;
import nl.mfarr.supernova.dtos.EmployeeResponseDto;
import nl.mfarr.supernova.models.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponseDto toDto(EmployeeEntity employeeEntity) {
        EmployeeResponseDto employeeDto = new EmployeeResponseDto();
        employeeDto.setEmployeeId(employeeEntity.getEmployeeId());
        employeeDto.setName(employeeEntity.getName());
        employeeDto.setEmail(employeeEntity.getEmail());
        employeeDto.setPhoneNumber(employeeEntity.getPhoneNumber());
        employeeDto.setRole(employeeEntity.getRole());
        employeeDto.setPhotoFileName(employeeEntity.getPhotoFileName());
        employeeDto.setDocumentFileName(employeeEntity.getDocumentFileName());
        return employeeDto;
    }

    public EmployeeEntity toEntity(EmployeeRequestDto employeeDto) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setName(employeeDto.getName());
        employeeEntity.setEmail(employeeDto.getEmail());
        employeeEntity.setPhoneNumber(employeeDto.getPhoneNumber());
        employeeEntity.setPassword(employeeDto.getPassword());
        employeeEntity.setRole(employeeDto.getRole());
        return employeeEntity;
    }
}
