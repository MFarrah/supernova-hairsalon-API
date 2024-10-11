// ValidatorService.java
package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.bookingDtos.BookingRequestDto;
import nl.mfarr.supernova.dtos.employeeDtos.EmployeeResponseDto;
import nl.mfarr.supernova.dtos.orderDtos.OrderUpsertRequestDto;
import nl.mfarr.supernova.dtos.scheduleDtos.ScheduleResponseDto;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.enums.TimeSlotStatus;
import nl.mfarr.supernova.exceptions.*;
import nl.mfarr.supernova.repositories.OrderRepository;
import nl.mfarr.supernova.repositories.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class ValidatorService {

    @Autowired
    private RosterRepository rosterRepository;
    @Autowired
    private OrderRepository orderRepository;

    public void validateEmployeeId(Long employeeId) {
        if (employeeId == null || employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
    }

    public void validateMonth(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Invalid month");
        }
    }

    public void validateYear(int year) {
        if (year < LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Invalid year");
        }
    }

    //later voor bookingService
    public void validateTimeSlotStatus(TimeSlotStatus status) {
        if (status != TimeSlotStatus.AVAILABLE) {
            if (status != TimeSlotStatus.BOOKED) {
                throw new TimeSlotBookedException("The requested timeslot is already booked");
            }
            if (status != TimeSlotStatus.UNAVAILABLE) {
                throw new TimeSlotUnavailableException("Employee is unavailable for the requested timeslot");
            }
        }
    }

    public void validateEmployeeExists(EmployeeResponseDto employee) {
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee does not exist");
        }
    }

    public void validateWorkingSchedule(Set<ScheduleResponseDto> workingSchedule) {
        if (workingSchedule == null || workingSchedule.isEmpty()) {
            throw new WorkingScheduleNotFoundException("Employee does not have a working schedule");
        }
    }

    public void validateRosterNotExists(EmployeeResponseDto employee, int month, int year) {
        List<RosterEntity> existingRosters = rosterRepository.findByEmployeeIdAndMonthAndYear(employee.getEmployeeId(), month, year);
        if (!existingRosters.isEmpty()) {
            throw new RosterAlreadyExistsException("Roster already exists for the given month");
        }
    }

    public boolean hasWorkingScheduleForDay(Set<ScheduleResponseDto> workingSchedule, LocalDate date) {
        return workingSchedule.stream()
                .anyMatch(schedule -> schedule.getDayOfWeek() == date.getDayOfWeek());
    }

    public void validateQualifiedOrderIds(List<Long> orderIds, Set<Long> qualifiedOrderIds) {
        for (Long orderId : orderIds) {
            if (!qualifiedOrderIds.contains(orderId)) {
                throw new IllegalArgumentException("Employee is not qualified for Order ID : " + orderId);
            }
        }
    }

    public void validateOrderUpsertRequirements(OrderUpsertRequestDto dto) {

        if (dto.getDescription()== null || dto.getDescription().isEmpty()) {
            throw new DescriptionRequiredException("Description is required");
        }
        if (orderRepository.existsByDescription(dto.getDescription())) {
            throw new DescriptionUniqueException("Description must be unique");
        }
        if (dto.getPrice() == null) {
            throw new PriceRequiredException("Price is required");
        }
        if (dto.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PriceMustBePositiveException("Price must be greater than 0");
        }
        if (dto.getDuration() <= 0) {
            throw new DurationPositiveException("Duration must be greater than 0");
        }
    }
    void validateBookingRequest(BookingRequestDto dto) {
        if (dto == null || dto.getDate() == null || dto.getStartTime() == null) {
            throw new IllegalArgumentException("BookingCustomerRequestDto and its date and start time cannot be null");
        }
    }
}