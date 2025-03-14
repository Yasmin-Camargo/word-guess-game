package com.projetotabia.word_guess_game.dtos;

import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
public class ReportDto {
    double averageWordLength;
    Character mostUsedVowel;
    String longestWord;
    List<String> levelFrequencyRanking;

    public ReportDto() {
        this.averageWordLength = 0;
        this.mostUsedVowel = null;
        this.longestWord = null;
        this.levelFrequencyRanking = null;
    }

    @Override
    public String toString() {
        return "\nReportDto{" +
                "\n\taverageWordLength=" + averageWordLength +
                ", \n\tmostUsedVowel=" + mostUsedVowel +
                ", \n\tlongestWord='" + longestWord + '\'' +
                ", \n\tlevelFrequencyRanking=" + levelFrequencyRanking +
                "\n}";
    }
}
