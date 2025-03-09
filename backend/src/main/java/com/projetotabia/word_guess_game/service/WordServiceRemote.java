package com.projetotabia.word_guess_game.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class WordServiceRemote {

    @Getter
    private WordsServiceRemote wordsService;

    @PostConstruct
    public void init() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            wordsService = (WordsServiceRemote) registry.lookup("WordsService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
