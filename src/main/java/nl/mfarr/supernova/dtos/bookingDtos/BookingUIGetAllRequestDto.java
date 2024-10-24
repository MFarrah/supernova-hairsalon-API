package nl.mfarr.supernova.dtos.bookingDtos;

import java.time.LocalDate;

public class BookingUIGetAllRequestDto {

    LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // zorg ervoor dat alle boekingen worden opgehaald vanaf de gegeven datum
}
