package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.CreateBookingRequestDto;
import com.example.bookmyshow.dtos.CreateBookingResponseDto;
import com.example.bookmyshow.dtos.CreateBookingResponseStatus;
import com.example.bookmyshow.dtos.ResponseStatus;
import com.example.bookmyshow.exceptions.ShowNotFoundException;
import com.example.bookmyshow.exceptions.ShowSeatNotFound;
import com.example.bookmyshow.exceptions.UserNotFoundException;
import com.example.bookmyshow.models.Booking;
import com.example.bookmyshow.services.BookingService;
import org.springframework.stereotype.Controller;


@Controller
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public CreateBookingResponseDto createBooking(CreateBookingRequestDto requestDto)  {
        CreateBookingResponseDto responseDto = new CreateBookingResponseDto();

        try {
            Booking booking = bookingService.createBooking(requestDto.getUserId(),
                    requestDto.getShowId(),
                    requestDto.getShowSeatIds());

            responseDto.setBookingId(booking.getId());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        }catch(Exception e){
            responseDto.setResponseStatus(ResponseStatus.FAILURE);

        }




        return responseDto;
    }
}
