package com.projetotabia.word_guess_game.dtos;

import jakarta.validation.constraints.NotBlank;

public record GameStartDto(
        @NotBlank String description,
        @NotBlank String synonymous) {
}
