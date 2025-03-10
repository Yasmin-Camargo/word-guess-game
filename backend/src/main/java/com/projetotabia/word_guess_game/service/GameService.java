package com.projetotabia.word_guess_game.service;

import com.projetotabia.word_guess_game.dtos.GameStartDto;
import com.projetotabia.word_guess_game.dtos.PromptResponseDto;
import com.projetotabia.word_guess_game.dtos.WordsRecordDto;
import com.projetotabia.word_guess_game.dtos.GameConfigDto;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class GameService {

    private PromptResponseDto gameState;
    private List<WordsRecordDto> wordHistory;
    private int numberAttempts;
    private ThreadPoolExecutor executor;

    @Setter
    private GameConfigDto gameConfig;

    public GameService() {
        gameConfig = new GameConfigDto("Fácil", "Tecnologia");
        wordHistory = new ArrayList<>();
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    }

    @Autowired
    private PromptExecutor promptExecutor;

    public GameStartDto startGame() throws RemoteException {
        if (wordHistory.isEmpty()) {
            wordHistory = getWordsService().getAllWords();
        }

        String prompt = "## Função\n" +
                "Você está participando de um *jogo de adivinhação de palavras*! Sua tarefa é fornecer uma **palavra**, sua **definição** e dois **sinônimos**. \n" +
                "\n" +
                "## Regras\n" +
                "- O nível de dificuldade deve ser: " + gameConfig.difficulty() + ".\n" +
                "- A palavra deve pertencer ao tema: " + gameConfig.theme() + ".\n" +
                "- Evite repetir palavras que já foram sorteadas: " + wordHistory.stream().map(WordsRecordDto::word).toList() + ".\n" +
                "Formato de saida esperado:" +
                "{\n" +
                "  \"word\": \"exemplo de palavra em português\",\n" +
                "  \"description\": \"Esse é um exemplo de descrição\",\n" +
                "  \"synonymous1\": \"alternativa 1\",\n" +
                "  \"synonymous2\": \"alternativa 2\"\n" +
                "}" +
                "\n" +
                "> A definição deve ser objetiva, clara, precisa, **sem** mencionar a palavra nem seus sinônimos. \n" +
                "> Certifique-se de que os sinônimos sejam termos com significados semelhantes e coerentes.";

        try {
            gameState = PromptResponseDto.fromString(promptExecutor.execute(prompt, null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("[Thread" + Thread.currentThread().getId() + "] [GameService.java] GPT: Palavra foi selecionada");
        numberAttempts = 3;

        return new GameStartDto(gameState.description(), gameState.synonymous1());
    }

    public String checkWord(String word){
        Boolean isFinalGame = false;
        LevenshteinDistance levenshtein = new LevenshteinDistance();

        if (gameState.word() == null || gameState.word().isEmpty()) {
            return "O jogo não foi iniciado ainda.";
        }

        word = normalizeWord(word);
        var distance = levenshtein.apply(word, gameState.word());
        numberAttempts -= 1;

        if (distance <= 1 && numberAttempts >= 0) {
            try {
                saveWordAsync();
                System.out.println("[Thread" + Thread.currentThread().getId() + "] [GameService.java] Partida finalizada");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return "Parabéns! Você acertou!!! A palavra era " + gameState.word() + "!";
        }
        else if (numberAttempts == 0 || numberAttempts < 0) {
            try {
                saveWordAsync();
                System.out.println("[Thread" + Thread.currentThread().getId() + "] [GameService.java] Partida finalizada");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            return "Suas tentativas acabaram. A palavra era: " + gameState.word();
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

    private void saveWordAsync() throws RemoteException {
        executor.submit(() -> {
            try {
                WordsServiceRemote wordsService = getWordsService();
                WordsRecordDto wordsRecordDto = new WordsRecordDto(null, gameState.word(), gameState.description(), gameState.synonymous1().concat(", " + gameState.synonymous2()), gameConfig.difficulty());
                wordsService.saveWord(wordsRecordDto);
                wordHistory = getWordsService().getAllWords();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("[Thread" + Thread.currentThread().getId() + "] [GameService.java] Async: Palavra salva no banco de dados");
        });
    }
}
