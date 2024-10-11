package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.timeSlotDtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import nl.mfarr.supernova.mappers.TimeSlotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private RosterRepository rosterRepository;
    @Autowired
    private TimeSlotMapper timeSlotMapper;
    @Autowired
    private EmployeeRepository employeeRepository;




}