package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.ShowNotFoundException;
import com.example.bookmyshow.exceptions.UserNotFoundException;
import com.example.bookmyshow.models.Booking;
import com.example.bookmyshow.models.Show;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.repositories.ShowRepository;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;

    public BookingService(UserRepository userRepository, ShowRepository showRepository) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
    }
//implementing 1st solution(taking over lock on the method)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking createBooking(Long userId, Long showId, List<Long> showSeatIds) throws UserNotFoundException, ShowNotFoundException {
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

        //2) get the show with given showId
        Optional<Show> optionalShow = showRepository.findById(showId);
        if(optionalShow.isEmpty()){
            throw new ShowNotFoundException("Show with given Id: " + showId + "not found !");
        }
        return null;
    }
}
