package com.projetotabia.word_guess_game.service;

import com.projetotabia.word_guess_game.dtos.WordsRecordDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface WordsServiceRemote extends Remote {
    WordsRecordDto saveWord(WordsRecordDto wordsRecordDto) throws RemoteException;
    List<WordsRecordDto> getAllWords() throws RemoteException;
    WordsRecordDto getOneWord(Long id) throws RemoteException;
    WordsRecordDto updateWord(WordsRecordDto wordBefore, WordsRecordDto wordNew) throws RemoteException;
    void deleteWord(Long id) throws RemoteException;
    void teste() throws RemoteException;
}
