package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.ScheduleRequestDto;
import nl.mfarr.supernova.dtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.mappers.ScheduleMapper;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        LocalDate startDate = requestDto.getStartDate();
        LocalDate endDate = requestDto.getEndDate();
        LocalTime startTime = requestDto.getStartTime();
        LocalTime endTime = requestDto.getEndTime();
        EmployeeEntity employee = employeeRepository.findById(requestDto.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));

        // Loop through each day between startDate and endDate
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            // Create a new ScheduleEntity for the day
            ScheduleEntity schedule = new ScheduleEntity();
            schedule.setDayOfWeek(dayOfWeek);
            schedule.setDate(date);
            schedule.setEmployee(employee);

            // Generate TimeSlots for the day
            List<TimeSlotEntity> timeSlots = new ArrayList<>();
            LocalTime slotStartTime = startTime;
            while (slotStartTime.isBefore(endTime)) {
                LocalTime slotEndTime = slotStartTime.plusMinutes(30);
                if (slotEndTime.isAfter(endTime)) {
                    slotEndTime = endTime; // Ensure not exceeding end time
                }

                // Create new TimeSlotEntity
                TimeSlotEntity timeSlot = new TimeSlotEntity();
                timeSlot.setStartTime(slotStartTime);
                timeSlot.setEndTime(slotEndTime);
                timeSlot.setSchedule(schedule);

                timeSlots.add(timeSlot);

                slotStartTime = slotEndTime; // Move to the next time slot
            }

            // Add the TimeSlots to the ScheduleEntity
            schedule.setTimeSlots(new HashSet<>(timeSlots));

            // Save the ScheduleEntity and TimeSlotEntity
            scheduleRepository.save(schedule);
            timeSlotRepository.saveAll(timeSlots);
        }

        // Return the response after saving the schedule
        ScheduleEntity savedSchedule = scheduleMapper.toEntity(requestDto);
        return scheduleMapper.toDto(savedSchedule);
    }

    public List<ScheduleResponseDto> getSchedulesForEmployee(Long employeeId) {
        return scheduleRepository.findByEmployeeEmployeeId(employeeId).stream()
                .map(scheduleMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean isEmployeeAvailable(Long employeeId, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
        return scheduleRepository.isEmployeeAvailable(employeeId, dayOfWeek, startTime, endTime);
    }

    // Delete a schedule
    public void deleteSchedule(Long scheduleId) {
        ScheduleEntity schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        scheduleRepository.delete(schedule);
    }
}
