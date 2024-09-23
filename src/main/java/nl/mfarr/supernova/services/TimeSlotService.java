package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.TimeSlotRequestDto;
import nl.mfarr.supernova.dtos.TimeSlotResponseDto;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.entities.TimeSlotEntity;
import nl.mfarr.supernova.mappers.TimeSlotMapper;
import nl.mfarr.supernova.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeSlotMapper timeSlotMapper;

    public List<TimeSlotResponseDto> getAvailableTimeSlots(Long employeeId, LocalDate date, LocalTime startTime, int duration) {
        LocalTime endTime = startTime.plusMinutes(duration);
        List<TimeSlotEntity> availableSlots = timeSlotRepository.findAvailableSlotsForEmployee(employeeId, date, startTime, endTime);
        return availableSlots.stream()
                .map(timeSlotMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean isEmployeeAvailable(Long employeeId, LocalDate date, LocalTime startTime, int duration) {
        List<TimeSlotEntity> availableSlots = timeSlotRepository.findAvailableSlotsForEmployee(employeeId, date, startTime, startTime.plusMinutes(duration));
        return !availableSlots.isEmpty();
    }

    // Create a new time slot
    public List<TimeSlotEntity> allocateTimeSlots(ScheduleEntity schedule) {
        LocalTime startTime = schedule.getStartTime();
        LocalTime endTime = schedule.getEndTime();
        LocalDate scheduleDate = schedule.getDate();

        List<TimeSlotEntity> timeSlots = new ArrayList<>();

        // Create time slots in 30-minute intervals between start and end times
        LocalTime slotStartTime = startTime;

        while (slotStartTime.isBefore(endTime)) {
            LocalTime slotEndTime = slotStartTime.plusMinutes(30);

            // Ensure the last time slot does not go beyond the end time
            if (slotEndTime.isAfter(endTime)) {
                slotEndTime = endTime;
            }

            // Create a new TimeSlotEntity
            TimeSlotEntity timeSlot = new TimeSlotEntity();
            timeSlot.setStartTime(slotStartTime);
            timeSlot.setEndTime(slotEndTime);
            timeSlot.setSchedule(schedule); // Associate the timeslot with the schedule
            timeSlot.setDate(scheduleDate); // Assign the date to the time slot

            // Add the time slot to the list
            timeSlots.add(timeSlot);

            // Move to the next slot
            slotStartTime = slotEndTime;
        }

        // Save all time slots to the repository (assuming a timeSlotRepository exists)
        timeSlotRepository.saveAll(timeSlots);

        return timeSlots;
    }


    // Delete a time slot
    public void deleteTimeSlot(Long timeSlotId) {
        TimeSlotEntity timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new RuntimeException("Time slot not found"));
        timeSlotRepository.delete(timeSlot);
    }
}
