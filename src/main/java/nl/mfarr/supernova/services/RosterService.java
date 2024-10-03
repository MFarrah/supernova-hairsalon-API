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

    public RosterEntity createRosterWithTimeSlots(Long employeeId) {
        EmployeeEntity employee = employeeService.findById(employeeId);
        LocalDate today = LocalDate.now();
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        RosterEntity roster = new RosterEntity();
        roster.setEmployee(employee);
        roster.setDate(today);
        roster.setWeek(today.get(WeekFields.ISO.weekOfWeekBasedYear()));
        roster.setMonth(today.getMonthValue());
        roster.setYear(today.getYear());

        List<RosterEntity.TimeSlot> timeSlots = new ArrayList<>();
        for (LocalDate date = today; !date.isAfter(endOfMonth); date = date.plusDays(1)) {
            LocalTime startTime = LocalTime.of(0, 0);
            while (startTime.isBefore(LocalTime.of(21, 0))) {
                RosterEntity.TimeSlot timeSlot = new RosterEntity.TimeSlot();
                timeSlot.setDate(date);
                timeSlot.setStartTime(startTime);
                timeSlot.setEndTime(startTime.plusMinutes(15));
                timeSlot.setStatus(TimeSlotStatus.AVAILABLE);
                timeSlots.add(timeSlot);
                startTime = startTime.plusMinutes(15);
            }
            // Add UNAVAILABLE slots from 21:00 to the next day's 09:00
            RosterEntity.TimeSlot unavailableSlot = new RosterEntity.TimeSlot();
            unavailableSlot.setDate(date);
            unavailableSlot.setStartTime(LocalTime.of(21, 0));
            unavailableSlot.setEndTime(LocalTime.of(23, 59));
            unavailableSlot.setStatus(TimeSlotStatus.UNAVAILABLE);
            timeSlots.add(unavailableSlot);
        }

        roster.setTimeSlots(timeSlots);
        return rosterRepository.save(roster);
    }

    public void copyWorkingScheduleToRoster(Long employeeId) {
        EmployeeEntity employee = employeeService.findById(employeeId);
        Set<ScheduleEntity> workingSchedule = employee.getWorkingSchedule();
        LocalDate today = LocalDate.now();
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        RosterEntity roster = new RosterEntity();
        roster.setEmployee(employee);
        roster.setDate(today);
        roster.setWeek(today.get(WeekFields.ISO.weekOfWeekBasedYear()));
        roster.setMonth(today.getMonthValue());
        roster.setYear(today.getYear());

        List<RosterEntity.TimeSlot> timeSlots = new ArrayList<>();
        for (LocalDate date = today; !date.isAfter(endOfMonth); date = date.plusDays(1)) {
            for (ScheduleEntity schedule : workingSchedule) {
                LocalTime startTime = schedule.getStartTime();
                LocalTime endTime = schedule.getEndTime();
                while (startTime.isBefore(endTime)) {
                    RosterEntity.TimeSlot timeSlot = new RosterEntity.TimeSlot();
                    timeSlot.setDate(date);
                    timeSlot.setStartTime(startTime);
                    timeSlot.setEndTime(startTime.plusMinutes(15));
                    timeSlot.setStatus(TimeSlotStatus.AVAILABLE);
                    timeSlots.add(timeSlot);
                    startTime = startTime.plusMinutes(15);
                }
            }
            // Add UNAVAILABLE slots from 21:00 to 09:00
            RosterEntity.TimeSlot unavailableSlot = new RosterEntity.TimeSlot();
            unavailableSlot.setDate(date);
            unavailableSlot.setStartTime(LocalTime.of(21, 0));
            unavailableSlot.setEndTime(LocalTime.of(23, 59));
            unavailableSlot.setStatus(TimeSlotStatus.UNAVAILABLE);
            timeSlots.add(unavailableSlot);
        }

        roster.setTimeSlots(timeSlots);
        rosterRepository.save(roster);
    }

    public RosterEntity createAndCopyRoster(Long employeeId) {
        EmployeeEntity employee = employeeService.findById(employeeId);
        LocalDate today = LocalDate.now();
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        RosterEntity roster = new RosterEntity();
        roster.setEmployee(employee);
        roster.setDate(today);
        roster.setWeek(today.get(WeekFields.ISO.weekOfWeekBasedYear()));
        roster.setMonth(today.getMonthValue());
        roster.setYear(today.getYear());

        List<RosterEntity.TimeSlot> timeSlots = new ArrayList<>();
        Set<ScheduleEntity> workingSchedule = employee.getWorkingSchedule();

        for (LocalDate date = today; !date.isAfter(endOfMonth); date = date.plusDays(1)) {
            for (ScheduleEntity schedule : workingSchedule) {
                LocalTime startTime = schedule.getStartTime();
                LocalTime endTime = schedule.getEndTime();
                while (startTime.isBefore(endTime)) {
                    RosterEntity.TimeSlot timeSlot = new RosterEntity.TimeSlot();
                    timeSlot.setDate(date);
                    timeSlot.setStartTime(startTime);
                    timeSlot.setEndTime(startTime.plusMinutes(15));
                    timeSlot.setStatus(TimeSlotStatus.AVAILABLE);
                    timeSlots.add(timeSlot);
                    startTime = startTime.plusMinutes(15);
                }
            }
            // Add UNAVAILABLE slots from 21:00 to 09:00
            RosterEntity.TimeSlot unavailableSlot = new RosterEntity.TimeSlot();
            unavailableSlot.setDate(date);
            unavailableSlot.setStartTime(LocalTime.of(21, 0));
            unavailableSlot.setEndTime(LocalTime.of(23, 59));
            unavailableSlot.setStatus(TimeSlotStatus.UNAVAILABLE);
            timeSlots.add(unavailableSlot);
        }

        roster.setTimeSlots(timeSlots);
        return rosterRepository.save(roster);
    }

    public RosterResponseDto getEmployeeMonthlyRoster(Long employeeId, int month) {
        EmployeeEntity employee = employeeService.findById(employeeId);
        List<RosterEntity> rosters = rosterRepository.findByEmployeeAndMonth(employee, month);
        if (rosters.isEmpty()) {
            throw new NoRosterFoundException("No roster found for the given employee and month");
        }
        return rosterMapper.toDto(rosters.get(0));
    }

    public boolean isEmployeeAvailable(DateTimeEmployeeCheckRequestDto requestDto, TimeSlotRequestDto timeSlotDto) {
        EmployeeEntity employee = employeeService.findById(requestDto.getEmployeeId());
        LocalDate date = requestDto.getDate();
        List<RosterEntity> rosters = rosterRepository.findByEmployeeAndDate(employee, date);

        if (rosters.isEmpty()) {
            return false;
        }

        RosterEntity roster = rosters.get(0); // Assuming one roster per day
        LocalTime requestedStartTime = timeSlotDto.getStartTime();
        LocalTime requestedEndTime = timeSlotDto.getEndTime();

        for (RosterEntity.TimeSlot timeSlot : roster.getTimeSlots()) {
            if (timeSlot.getStatus() == TimeSlotStatus.AVAILABLE &&
                    !requestedStartTime.isBefore(timeSlot.getStartTime()) &&
                    !requestedEndTime.isAfter(timeSlot.getEndTime())) {
                return true;
            }
        }

        return false;
    }
}