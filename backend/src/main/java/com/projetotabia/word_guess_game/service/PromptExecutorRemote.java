package com.projetotabia.word_guess_game.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PromptExecutorRemote extends Remote {
    String execute(@NotNull String prompt, @Nullable String message) throws IOException, RemoteException;
}
