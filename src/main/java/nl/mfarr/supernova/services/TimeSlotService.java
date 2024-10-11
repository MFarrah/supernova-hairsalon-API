package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.timeSlotDtos.EmployeeMonthRequestDto;
import nl.mfarr.supernova.dtos.timeSlotDtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import nl.mfarr.supernova.mappers.TimeSlotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TimeSlotService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private TimeSlotMapper timeSlotMapper;

    public List<TimeSlotResponseDto> getEmployeeMonthRoster(EmployeeMonthRequestDto employeeMonthRequestDto) {
        EmployeeEntity employee = employeeRepository.findById(employeeMonthRequestDto.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));
        LocalDate dateRange = LocalDate.of(employeeMonthRequestDto.getYear(), employeeMonthRequestDto.getMonth(), 1);
        //filter all slots with the same month and year in 'date' field
        List<TimeSlotEntity> timeSlots = timeSlotRepository.findByEmployeeAndDateBetween(employee, dateRange.withDayOfMonth(1), dateRange.withDayOfMonth(dateRange.lengthOfMonth()));
        return timeSlotMapper.toDtoList(timeSlots);
    }
}