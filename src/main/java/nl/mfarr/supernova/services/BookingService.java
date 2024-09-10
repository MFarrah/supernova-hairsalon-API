package nl.mfarr.supernova.services;

import nl.mfarr.supernova.dtos.BookingRequestDto;
import nl.mfarr.supernova.entities.BookingEntity;
import nl.mfarr.supernova.entities.EmployeeEntity;
import nl.mfarr.supernova.entities.ScheduleEntity;
import nl.mfarr.supernova.enums.BookingStatus;
import nl.mfarr.supernova.exceptions.ResourceNotFoundException;
import nl.mfarr.supernova.repositories.BookingRepository;
import nl.mfarr.supernova.repositories.EmployeeRepository;
import nl.mfarr.supernova.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public void createBooking(BookingRequestDto bookingRequestDto) {
        // Zoek de medewerker op basis van ID
        EmployeeEntity employee = employeeRepository.findById(bookingRequestDto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Valideer beschikbaarheid van de medewerker
        validateEmployeeAvailability(employee, bookingRequestDto.getDate(), bookingRequestDto.getStartTime(), bookingRequestDto.getEndTime());

        BookingEntity booking = new BookingEntity();
        // Vul de velden van de boeking in met de DTO-informatie
        bookingRepository.save(booking);
    }

    private void validateEmployeeAvailability(EmployeeEntity employee, LocalDate date, LocalTime startTime, LocalTime endTime) {
        ScheduleEntity schedule = scheduleRepository.findByEmployeeAndDayOfWeek(employee, date.getDayOfWeek())
                .orElseThrow(() -> new RuntimeException("Employee is not available on this day"));

        if (startTime.isBefore(schedule.getStartTime()) || endTime.isAfter(schedule.getEndTime())) {
            throw new RuntimeException("Employee is not available during the selected time");
        }
    }

    public BookingEntity viewBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public void confirmBooking(Long bookingId) {
        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);
    }

    public List<EmployeeEntity> getAvailableEmployees(LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<EmployeeEntity> availableEmployees = employeeRepository.findAll().stream()
                .filter(employee -> {
                    ScheduleEntity schedule = scheduleRepository.findByEmployeeAndDayOfWeek(employee, date.getDayOfWeek())
                            .orElse(null);

                    if (schedule == null) {
                        return false;
                    }

                    return !bookingRepository.existsByEmployeeAndDateAndStartTime(employee.getId(), date, startTime);
                })
                .collect(Collectors.toList());

        return availableEmployees;
    }


}
