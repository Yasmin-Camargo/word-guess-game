package com.projetotabia.word_guess_game.service;


import com.projetotabia.word_guess_game.dtos.GameStartDto;
import com.projetotabia.word_guess_game.dtos.WordsRecordDto;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.Normalizer;

@Service
public class GameService {

    private String currentWord;
    private int numberAttempts;


    public GameStartDto startGame() throws RemoteException {
        currentWord = "teste";
        GameStartDto gameStartDto = new GameStartDto("descrição teste", "sinonimo teste");
        numberAttempts = 3;

        try {
            WordsServiceRemote wordsService = getWordsService();
            WordsRecordDto wordsRecordDto = new WordsRecordDto(null, currentWord, "teste", "teste", "teste");
            wordsService.saveWord(wordsRecordDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameStartDto;
    }

    public String checkWord(String word){
        if (currentWord == null || currentWord == null || currentWord.isEmpty()) {
            return "O jogo não foi iniciado ainda.";
        }

        word = normalizeWord(word);
        boolean victory = word.equals(normalizeWord(currentWord));
        numberAttempts -= 1;

        if (victory && numberAttempts >= 0) {
            return "Parabéns! Você acertou!!! A palavra era " + currentWord + "!";
        }
        else if (numberAttempts == 0 || numberAttempts < 0){
            return "Suas tentativas acabaram. A palavra era: " + currentWord;
        }
        else if (word.equals("showsynonymous")){
            return "Dica revelada. Restam " + numberAttempts + " tentativas.";
        }
        else {
            return "Você errou! Tente novamente. Restam " + numberAttempts + " tentativas.";
        }
    }

    public String getCurrentWord(){
        return currentWord;
    }

    public String normalizeWord(String input) {
        String normalized = Normalizer.normalize(input.toLowerCase(), Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").replaceAll("ç", "c").replaceAll("-", "");
    }


    private WordsServiceRemote getWordsService() throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            return (WordsServiceRemote) registry.lookup("WordsService");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Failed to get WordsService", e);
        }
    }
}
