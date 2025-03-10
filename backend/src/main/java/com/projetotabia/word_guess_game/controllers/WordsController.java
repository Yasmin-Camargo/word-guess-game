package com.projetotabia.word_guess_game.controllers;

import com.projetotabia.word_guess_game.dtos.WordsRecordDto;
import com.projetotabia.word_guess_game.service.WordsServiceRemote;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/words")
public class WordsController {

    @PostMapping
    public ResponseEntity<WordsRecordDto> saveWord(@RequestBody @Valid WordsRecordDto wordsRecordDto) throws RemoteException {
        return ResponseEntity.status(HttpStatus.CREATED).body(wordsService().saveWord(wordsRecordDto));
    }

    @GetMapping
    public ResponseEntity<List<WordsRecordDto>> getAllWords() throws RemoteException {
        return ResponseEntity.status(HttpStatus.OK).body(wordsService().getAllWords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneWord(
            @PathVariable(value = "id") Long id) throws RemoteException {

        WordsRecordDto wordO = wordsService().getOneWord(id);
        if (wordO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Word not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(wordO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateWord(
            @PathVariable(value = "id") Long id,
            @RequestBody @Valid WordsRecordDto wordsRecordDto) throws RemoteException {

        var wordsService = wordsService();
        WordsRecordDto wordO = wordsService.getOneWord(id);
        if (wordO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Word not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(wordsService.updateWord(wordO, wordsRecordDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWord(@PathVariable(value = "id") Long id) throws RemoteException {
        var wordsService = wordsService();
        if (wordsService.getOneWord(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Word not found");
        }
        wordsService.deleteWord(id);
        return ResponseEntity.status(HttpStatus.OK).body("Word deleted successfully");
    }

    private WordsServiceRemote wordsService() throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            return (WordsServiceRemote) registry.lookup("WordsService");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException("Failed to get WordsService", e);
        }
    }
}
