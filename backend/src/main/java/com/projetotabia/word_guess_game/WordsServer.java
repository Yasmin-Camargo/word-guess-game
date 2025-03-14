package com.projetotabia.word_guess_game;

import com.projetotabia.word_guess_game.service.WordsServiceRemote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SpringBootApplication
@PropertySource("classpath:application-wordsserver.properties")
public class WordsServer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WordsServer.class);
        app.setAdditionalProfiles("wordsserver");
        ApplicationContext context = app.run(args);
        try {
            WordsServiceRemote wordsService = context.getBean(WordsServiceRemote.class);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("WordsService", wordsService);
            System.out.println("RMI Server WordsService is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
