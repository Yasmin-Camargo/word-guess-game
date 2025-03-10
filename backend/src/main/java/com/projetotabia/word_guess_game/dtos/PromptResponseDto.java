package com.projetotabia.word_guess_game.dtos;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import java.io.IOException;

public record PromptResponseDto(
        @NotBlank String word,
        @NotBlank String description,
        @Nullable String synonymous1,
        @Nullable String synonymous2) {

    public static PromptResponseDto fromString(String input) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(input, PromptResponseDto.class);
    }
}
