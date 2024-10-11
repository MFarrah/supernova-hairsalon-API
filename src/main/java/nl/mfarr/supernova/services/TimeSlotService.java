package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.timeSlotDtos.*;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.exceptions.BookingNotFoundException;
import nl.mfarr.supernova.exceptions.EmployeeNotFoundException;
import nl.mfarr.supernova.exceptions.RosterNotFoundException;
import nl.mfarr.supernova.exceptions.TimeSlotNotFoundException;
import nl.mfarr.supernova.repositories.BookingRepository;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import nl.mfarr.supernova.mappers.TimeSlotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class TimeSlotService {


    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RosterRepository rosterRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private TimeSlotMapper timeSlotMapper;

    public List<TimeSlotResponseDto> getEmployeeMonthRoster(EmployeeMonthRequestDto employeeMonthRequestDto) {
        EmployeeEntity employee = employeeRepository.findById(employeeMonthRequestDto.getEmployeeId()).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        LocalDate dateRange = LocalDate.of(employeeMonthRequestDto.getYear(), employeeMonthRequestDto.getMonth(), 1);
        //filter all slots with the same month and year in 'date' field
        List<TimeSlotEntity> timeSlots = timeSlotRepository.findByEmployeeAndDateBetween(employee, dateRange.withDayOfMonth(1), dateRange.withDayOfMonth(dateRange.lengthOfMonth()));
        return timeSlotMapper.toDtoList(timeSlots);
    }

    public List<TimeSlotResponseDto> getEmployeeWeekRoster(EmployeeWeekRequestDto employeeWeekRequestDto) {
        EmployeeEntity employee = employeeRepository.findById(employeeWeekRequestDto.getEmployeeId()).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        List<TimeSlotEntity> timeSlots = timeSlotRepository.findByWeekAndEmployee(employeeWeekRequestDto.getWeek(), employee);
        return timeSlotMapper.toDtoList(timeSlots);
    }

    public List<TimeSlotResponseDto> getEmployeeDayRoster(EmployeeDayRequestDto employeeDayRequestDto) {
        EmployeeEntity employee = employeeRepository.findById(employeeDayRequestDto.getEmployeeId()).orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        List<TimeSlotEntity> timeSlots = timeSlotRepository.findByDateAndEmployee(employeeDayRequestDto.getDate(), employee);
        return timeSlotMapper.toDtoList(timeSlots);
    }


}