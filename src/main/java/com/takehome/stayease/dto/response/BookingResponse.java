package com.takehome.stayease.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookingResponse {

    private Long bookingId;
    private Long hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}

