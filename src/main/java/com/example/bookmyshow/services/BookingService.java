package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.ShowNotFoundException;
import com.example.bookmyshow.exceptions.ShowSeatNotFound;
import com.example.bookmyshow.exceptions.UserNotFoundException;
import com.example.bookmyshow.models.*;
import com.example.bookmyshow.repositories.BookingRepository;
import com.example.bookmyshow.repositories.ShowRepository;
import com.example.bookmyshow.repositories.ShowSeatRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.cglib.core.Block;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PriceCalculator priceCalculator;
    private BookingRepository bookingRepository;

    public BookingService(UserRepository userRepository, ShowRepository showRepository,
                          ShowSeatRepository showSeatRepository, PriceCalculator priceCalculator, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.priceCalculator = priceCalculator;
        this.bookingRepository = bookingRepository;
    }
//implementing 1st solution(taking over lock on the method)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking createBooking(Long userId, Long showId, List<Long> showSeatIds) throws UserNotFoundException, ShowNotFoundException, ShowSeatNotFound {
        /*
        1) Get the user with the given userId
        2) get the show with given showId
        3)get the list of showSeats with the given id
        ----------------Take A Lock--------------
        4)check if all the seats are available or not
        5)if not, throw an exception
        6)if yes, mark the status of all the seats as Blocked
        ----------------Release the Lock-----------
        7)save the changes in the DB as well
        8)create the Booking with pending status[save booking obj to db]
        9)return booking obj;
         */
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User with given id: " + userId + "not found !");
        }

        User user = optionalUser.get();
        //2) get the show with given showId
        Optional<Show> optionalShow = showRepository.findById(showId);
        if(optionalShow.isEmpty()){
            throw new ShowNotFoundException("Show with given Id: " + showId + "not found !");
        }
        Show show = optionalShow.get();

        //3)get the list of ShowSeats
        //4)check if all the seats are available or not
        //5)if not, throw an exception
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        for(ShowSeat showSeat : showSeats){
            if(! showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)){
                throw new ShowSeatNotFound("Show with the given Seats: " + showSeat.getId() + " not found!");
            }
        }
        //6)if yes, mark the status of all the seats as Blocked
        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BOOKED);
            //7)save the changes in the DB as well

            showSeatRepository.save(showSeat);
        }

        //8)create the Booking with pending status[save booking obj to db]
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setCreatedAt(new Date());
        booking.setUser(user);
        booking.setShow(show);
        booking.setPayments(new ArrayList<>());
        booking.setShowSeat(showSeats);
        booking.setAmount(priceCalculator.calculatePrice(show, showSeats));

        return bookingRepository.save(booking);


    }
}
