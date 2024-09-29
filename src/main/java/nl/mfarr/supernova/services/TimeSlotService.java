package nl.mfarr.supernova.services;


import nl.mfarr.supernova.mappers.TimeSlotMapper;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeSlotMapper timeSlotMapper;


}
