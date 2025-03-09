package com.projetotabia.word_guess_game.repositories;

import com.projetotabia.word_guess_game.models.WordsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WordsRepository extends JpaRepository<WordsModel, Long> {
}
