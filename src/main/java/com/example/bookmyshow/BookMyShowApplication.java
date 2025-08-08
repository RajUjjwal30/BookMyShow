package com.example.bookmyshow;

import com.example.bookmyshow.models.BaseModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

public class BookMyShowApplication {

    public static void main(String[] args) {




        SpringApplication.run(BookMyShowApplication.class, args);
    }

}
