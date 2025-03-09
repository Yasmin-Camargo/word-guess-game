package com.projetotabia.word_guess_game.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;


public record WordsRecordDto (
        @Nullable Long idWord,
        @NotBlank String word,
        @NotBlank String description,
        @NotBlank String synonymous,
        @NotBlank String level) implements Serializable {
    private static final long serialVersionUID = 1L;
}
