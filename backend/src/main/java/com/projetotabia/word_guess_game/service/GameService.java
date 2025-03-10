package com.projetotabia.word_guess_game.service;


import com.projetotabia.word_guess_game.dtos.GameStartDto;
import com.projetotabia.word_guess_game.dtos.WordsRecordDto;
import com.projetotabia.word_guess_game.dtos.promptRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.text.similarity.LevenshteinDistance;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.Normalizer;
import java.util.List;

@Service
public class GameService {

    private String currentWord;
    private int numberAttempts;

    @Autowired
    private PromptExecutor promptExecutor;

    public GameStartDto startGame() throws RemoteException {
        var request = new promptRequestDto("Dificil", "Tecnologia", List.of());

        String prompt = "## Função\n" +
                "Você está participando de um *jogo de adivinhação de palavras*! Sua tarefa é fornecer uma **palavra**, sua **definição** e dois **sinônimos**. \n" +
                "\n" +
                "## Regras\n" +
                "- O nível de dificuldade deve ser: " + request.difficulty() + ".\n" +
                "- A palavra deve pertencer ao tema: " + request.theme() + ".\n" +
                "- Evite repetir palavras que já foram sorteadas: " + request.wordHistory() + ".\n" +
                "\n" +
                "> A definição deve ser objetiva, clara e precisa, **sem** mencionar a palavra nem seus sinônimos. \n" +
                "> Certifique-se de que os sinônimos sejam termos com significados semelhantes e coerentes.";

        var response = promptExecutor.execute(prompt, null);
        System.out.println(response);

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
