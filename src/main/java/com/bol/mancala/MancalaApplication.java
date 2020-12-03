package com.bol.mancala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@SpringBootApplication
@EnableMapRepositories
public class MancalaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MancalaApplication.class, args);
    }

}
