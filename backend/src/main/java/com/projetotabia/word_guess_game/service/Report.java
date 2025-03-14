package com.projetotabia.word_guess_game.service;

import com.google.common.util.concurrent.Uninterruptibles;
import com.projetotabia.word_guess_game.dtos.ReportDto;
import com.projetotabia.word_guess_game.dtos.WordsRecordDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static java.util.Map.*;

@Service
public class Report {
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    private List<WordsRecordDto> wordHistory;

    public ReportDto generateParallelReport(List<WordsRecordDto> wordHistory) {
        this.wordHistory = wordHistory;

        CountDownLatch latch = new CountDownLatch(4);
        ReportDto report = new ReportDto();

        executor.submit(() -> {
            try {
                report.setAverageWordLength(wordHistory.stream()
                        .mapToInt(wordRecord -> wordRecord.word().length())
                        .average().orElse(0));
            } finally {
                latch.countDown();
            }
        });

        executor.submit(() -> {
            try {
                report.setMostUsedVowel(findMostUsedVowel());
            } finally {
                latch.countDown();
            }
        });

        executor.submit(() -> {
            try {
                report.setLongestWord(wordHistory.stream()
                        .map(WordsRecordDto::word)
                        .max(Comparator.comparingInt(String::length))
                        .orElse(""));
            } finally {
                latch.countDown();
            }
        });

        executor.submit(() -> {
            try {
                report.setLevelFrequencyRanking(rankLevels());
            } finally {
                latch.countDown();
            }
        });

        Uninterruptibles.awaitUninterruptibly(latch);
        return report;
    }

    private Character findMostUsedVowel() {
        String vowels = "aeiouAEIOU";
        return wordHistory.stream()
                .flatMap(wordRecord -> wordRecord.word().chars().mapToObj(c -> (char) c))
                .filter(c -> vowels.indexOf(c) != -1)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                .entrySet().stream()
                .max(Entry.comparingByValue())
                .map(Entry::getKey)
                .orElse('?');
    }

    private List<String> rankLevels() {
        return wordHistory.stream()
                .collect(Collectors.groupingBy(WordsRecordDto::level, Collectors.counting()))
                .entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
