package com.example.bookmyshow.services;

import com.example.bookmyshow.exceptions.UserNotFoundException;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(String name, String email, String password){



        /*
        1.)Check if the user already exists with the given user id or not
        if yes, ask them to log in
        2.)if not, create a new User object with given details
        3.)save it to db
        */
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);

        return user;
    }

    public User login(String email, String password) throws UserNotFoundException {
        //first, check if the user is present or not
        Optional<User> optionalUser = userRepository.findAllByEmail(email);
            if(optionalUser.isEmpty()){
                throw new UserNotFoundException("User with this email "+ email + "not found !");
            }

            User user = optionalUser.get();

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            //compare the password
            if(bCryptPasswordEncoder.matches(password,user.getPassword())){
                //login successful
                return user;
            }
        throw new RuntimeException("User Id/ password incorrect");
    }
}
