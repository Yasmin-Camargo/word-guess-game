package com.projetotabia.word_guess_game.service;

import com.projetotabia.word_guess_game.dtos.WordsRecordDto;
import com.projetotabia.word_guess_game.models.WordsModel;
import com.projetotabia.word_guess_game.repositories.WordsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordsService extends UnicastRemoteObject implements WordsServiceRemote {

    @Autowired
    WordsRepository wordsRepository;

    public WordsService() throws RemoteException {
        super();
    }

    @Override
    public WordsRecordDto saveWord(WordsRecordDto wordsRecordDto) throws RemoteException {
        System.out.println("[Thread" + Thread.currentThread().getId() + "] [WordsService.java] Salvando palavra");
        var wordsModel = new WordsModel();
        BeanUtils.copyProperties(wordsRecordDto, wordsModel);
        WordsModel savedModel = wordsRepository.save(wordsModel);
        return convertToDto(savedModel);
    }

    @Override
    public List<WordsRecordDto> getAllWords() throws RemoteException {
        System.out.println("[Thread" + Thread.currentThread().getId() + "] [WordsService.java] Buscando todas as palavras");
        return wordsRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WordsRecordDto getOneWord(Long id) throws RemoteException {
        System.out.println("[Thread" + Thread.currentThread().getId() + "] [WordsService.java] Buscando palavra");
        return wordsRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null);
    }

    @Override
    public WordsRecordDto updateWord(WordsRecordDto wordBefore, WordsRecordDto wordNew) throws RemoteException {
        System.out.println("[Thread" + Thread.currentThread().getId() + "] [WordsService.java] Atualizando palavra");
        WordsModel wordModel = new WordsModel();
        BeanUtils.copyProperties(wordBefore, wordModel);
        BeanUtils.copyProperties(wordNew, wordModel);
        WordsModel updatedModel = wordsRepository.save(wordModel);
        return convertToDto(updatedModel);
    }

    @Override
    public void deleteWord(Long id) throws RemoteException {
        System.out.println("[Thread" + Thread.currentThread().getId() + "] [WordsService.java] Deletando palavra");
        wordsRepository.deleteById(id);
    }

    @Override
    public void teste(){
        System.out.println("[Thread" + Thread.currentThread().getId() + "] [WordsService.java] Teste");
    }

    private WordsRecordDto convertToDto(WordsModel wordsModel) {
        return new WordsRecordDto(
                wordsModel.getIdWord(),
                wordsModel.getWord(),
                wordsModel.getDescription(),
                wordsModel.getSynonymous(),
                wordsModel.getLevel()
        );
    }
}
