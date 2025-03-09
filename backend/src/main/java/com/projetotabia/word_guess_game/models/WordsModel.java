package com.projetotabia.word_guess_game.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter @Setter
@Entity
@Table(name = "TB_WORDS")
public class WordsModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWord;

    private String word;

    private String description;

    private String synonymous;

    private String level;
}
