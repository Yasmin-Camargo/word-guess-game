package com.projetotabia.word_guess_game.service;


import com.projetotabia.word_guess_game.dtos.GameStartDto;
import com.projetotabia.word_guess_game.dtos.PromptResponseDto;
import com.projetotabia.word_guess_game.dtos.WordsRecordDto;
import com.projetotabia.word_guess_game.dtos.GameConfigDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    @Getter
    private String currentWord;

    private List<String> wordHistory;

    private int numberAttempts;

    @Setter
    private GameConfigDto gameConfig;

    public GameService() {
        gameConfig = new GameConfigDto("Dificil", "Tecnologia");
        wordHistory = new ArrayList<>();;
    }

    @Autowired
    private PromptExecutor promptExecutor;

    public GameStartDto startGame() throws RemoteException {
        String prompt = "## Função\n" +
                "Você está participando de um *jogo de adivinhação de palavras*! Sua tarefa é fornecer uma **palavra**, sua **definição** e dois **sinônimos**. \n" +
                "\n" +
                "## Regras\n" +
                "- O nível de dificuldade deve ser: " + gameConfig.difficulty() + ".\n" +
                "- A palavra deve pertencer ao tema: " + gameConfig.theme() + ".\n" +
                "- Evite repetir palavras que já foram sorteadas: " + wordHistory + ".\n" +
                "Formato de saida esperado:" +
                "{\n" +
                "  \"word\": \"example\",\n" +
                "  \"description\": \"This is an example description.\",\n" +
                "  \"synonymous1\": \"alternative1\",\n" +
                "  \"synonymous2\": \"alternative2\"\n" +
                "}" +
                "\n" +
                "> A definição deve ser objetiva, clara e precisa, **sem** mencionar a palavra nem seus sinônimos. \n" +
                "> Certifique-se de que os sinônimos sejam termos com significados semelhantes e coerentes.";

        PromptResponseDto response = null;
        try {
            response = PromptResponseDto.fromString(promptExecutor.execute(prompt, null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(response);

        currentWord = response.word();
        GameStartDto gameStartDto = new GameStartDto(response.description(), response.synonymous1());
        numberAttempts = 3;

        try {
            WordsServiceRemote wordsService = getWordsService();
            WordsRecordDto wordsRecordDto = new WordsRecordDto(null, currentWord, "teste", "teste", "teste");
            wordsService.saveWord(wordsRecordDto);
            wordHistory.add(currentWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameStartDto;
    }

    public String checkWord(String word){
        LevenshteinDistance levenshtein = new LevenshteinDistance();

        if (currentWord == null || currentWord.isEmpty()) {
            return "O jogo não foi iniciado ainda.";
        }

        word = normalizeWord(word);
        var distance = levenshtein.apply(word, currentWord);
        numberAttempts -= 1;

        if (distance <= 1 && numberAttempts >= 0) {
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
