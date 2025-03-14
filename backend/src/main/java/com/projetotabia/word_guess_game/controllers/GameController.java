package com.projetotabia.word_guess_game.controllers;

import com.projetotabia.word_guess_game.dtos.GameConfigDto;
import com.projetotabia.word_guess_game.dtos.GameStartDto;
import com.projetotabia.word_guess_game.service.GameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/start")
    public ResponseEntity<GameStartDto> startGame() throws RemoteException {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.startGame());
    }

    @GetMapping("/check/{word}")
    public ResponseEntity<String> checkWord(
            @PathVariable(value = "word") String word) {
        return ResponseEntity.status(HttpStatus.OK).body(gameService.checkWord(word));
    }

    @PostMapping("/config")
    public ResponseEntity<Object> setConfigGame(
            @RequestBody @Valid GameConfigDto gameConfig) throws RemoteException {
        gameService.setGameConfig(gameConfig);
        System.out.println("Configurations updated: " + gameConfig);
        return ResponseEntity.status(HttpStatus.OK).body("Configurations updated");
    }

}


