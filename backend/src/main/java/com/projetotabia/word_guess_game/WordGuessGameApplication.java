package com.projetotabia.word_guess_game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-wordsgame.properties")
public class WordGuessGameApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WordGuessGameApplication.class);
        app.setAdditionalProfiles("wordsgame");
        app.run(args);
    }
}
