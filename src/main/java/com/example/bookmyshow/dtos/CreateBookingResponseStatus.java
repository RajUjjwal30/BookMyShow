package com.example.bookmyshow.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
public class CreateBookingResponseStatus {
    private Long bookingId;
    private ResponseStatus responseStatus;

}
