package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public void createSchedule(ScheduleRequestDto scheduleRequestDto) {
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setDayOfWeek(scheduleRequestDto.getDayOfWeek());
        schedule.setStartTime(scheduleRequestDto.getStartTime());
        schedule.setEndTime(scheduleRequestDto.getEndTime());

        scheduleRepository.save(schedule);
    }

    public void updateSchedule(Long scheduleId, ScheduleRequestDto scheduleRequestDto) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        schedule.setDayOfWeek(scheduleRequestDto.getDayOfWeek());
        schedule.setStartTime(scheduleRequestDto.getStartTime());
        schedule.setEndTime(scheduleRequestDto.getEndTime());

        scheduleRepository.save(schedule);
    }

    public ScheduleEntity viewSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }
}
