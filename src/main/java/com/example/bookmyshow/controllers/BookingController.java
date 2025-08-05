package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.CreateBookingRequestDto;
import com.example.bookmyshow.dtos.CreateBookingResponseStatus;
import com.example.bookmyshow.services.BookingService;
import org.springframework.stereotype.Controller;


@Controller
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public CreateBookingResponseStatus createBooking(CreateBookingRequestDto requestDto){
        return null;
    }
}
