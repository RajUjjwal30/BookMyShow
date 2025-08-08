package com.example.bookmyshow.controllers;

import com.example.bookmyshow.dtos.*;
import com.example.bookmyshow.exceptions.UserNotFoundException;
import com.example.bookmyshow.models.User;
import com.example.bookmyshow.services.UserService;
import lombok.Getter;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public SignupResponseDto signUp(SignupRequestDto requestDto){
        //TODO : handle exception
        User user = userService.signUp(requestDto.getName(),
                requestDto.getEmail(), requestDto.getPassword());

        SignupResponseDto responseDto = new SignupResponseDto();
        responseDto.setUserId(user.getId());
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        return responseDto;
    }

    public LogInResponseDto login(LogInRequestDto requestDto){
        LogInResponseDto responseDto = new LogInResponseDto();
        try {

            User user = userService.login(requestDto.getEmail(), requestDto.getPassword());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }

        return responseDto;
    }
}
