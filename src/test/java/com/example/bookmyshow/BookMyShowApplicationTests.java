package com.example.bookmyshow;

import com.example.bookmyshow.controllers.UserController;
import com.example.bookmyshow.dtos.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookMyShowApplicationTests {
    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSignUpFunctionality(){
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setName("Ujjwal Raj");
        requestDto.setEmail("ujjwal@gmail.com");
        requestDto.setPassword("password");

        SignupResponseDto responseDto = userController.signUp(requestDto);

        System.out.println(responseDto.getUserId());
    }


    @Test
    public void testLoginFunctionality(){
        LogInRequestDto requestDto = new LogInRequestDto();
        requestDto.setEmail("ujjwal@gmail.com");
        requestDto.setPassword("passwor");

        LogInResponseDto responseDto = userController.login(requestDto);

        if(responseDto.getResponseStatus().equals(ResponseStatus.SUCCESS)){
            System.out.println("Login successful");
        }else{
            System.out.println("Login failed");
        }
    }

}
