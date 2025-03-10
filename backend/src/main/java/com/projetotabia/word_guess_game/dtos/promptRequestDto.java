package com.projetotabia.word_guess_game.dtos;

import java.util.List;

public record promptRequestDto(String difficulty, String theme, List<String> wordHistory) {
}
