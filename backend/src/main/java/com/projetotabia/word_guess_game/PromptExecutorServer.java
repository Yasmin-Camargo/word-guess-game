package com.projetotabia.word_guess_game;

import com.projetotabia.word_guess_game.service.PromptExecutorRemote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SpringBootApplication
@PropertySource("classpath:application-promptexecutorserver.properties")
public class PromptExecutorServer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PromptExecutorServer.class);
        app.setAdditionalProfiles("promptExecutorServer");
        ApplicationContext context = app.run(args);
        try {
            PromptExecutorRemote promptExecutor = context.getBean(PromptExecutorRemote.class);
            Registry registry = LocateRegistry.createRegistry(2099);
            registry.bind("PromptExecutor", promptExecutor);
            System.out.println("RMI PromptExecutor Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
