// RosterService.java
package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.DateTimeEmployeeCheckRequestDto;
import nl.mfarr.supernova.dtos.RosterResponseDto;
import nl.mfarr.supernova.dtos.TimeSlotRequestDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.NoRosterFoundException;
import nl.mfarr.supernova.mappers.RosterMapper;
import nl.mfarr.supernova.repositories.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RosterService {

    private final RosterRepository rosterRepository;
    private final EmployeeService employeeService;
    private final RosterMapper rosterMapper;

    @Autowired
    public RosterService(RosterRepository rosterRepository, EmployeeService employeeService, RosterMapper rosterMapper) {
        this.rosterRepository = rosterRepository;
        this.employeeService = employeeService;
        this.rosterMapper = rosterMapper;
    }


}