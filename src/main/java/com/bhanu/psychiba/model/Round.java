package com.bhanu.psychiba.model;

import com.bhanu.psychiba.exceptions.InvalidActionForGameStateException;
import com.bhanu.psychiba.exceptions.InvalidInputException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "rounds")
public class Round extends Auditable {

    @ManyToOne
    @Getter
    @Setter
    @JsonBackReference
    private Game game;

    @NotNull
    @Getter
    @Setter
    private int roundNumber;

    @ManyToOne
    @Getter
    @Setter
    @NotNull
    private Question question;

    @ManyToMany
    @Getter
    @Setter
    @JsonManagedReference
    private Map<Player, PlayerAnswer> submittedAnswers = new HashMap<>();

    @ManyToMany
    @Getter
    @Setter
    @JsonManagedReference
    private Map<Player, PlayerAnswer> selectedAnswers = new HashMap<>();

    @ManyToMany
    @Getter
    @Setter
    private Set<Player> readyPlayers = new HashSet<>();

    public Round(@NotNull Game game, Question question, @NotNull int roundNumber) {
        this.game = game;
        this.question = question;
        this.roundNumber = roundNumber;
    }

    void submitAnswer(Player player, String answer) throws InvalidActionForGameStateException {
        if (submittedAnswers.containsKey(player)) {
            throw new InvalidActionForGameStateException("Already submitted.");
        }
        PlayerAnswer playerAnswer = new PlayerAnswer();
        submittedAnswers.put(player, playerAnswer);
    }

    void selectAnswer(Player player, PlayerAnswer playerAnswer) throws InvalidInputException {
        if (!playerAnswer.getRound().equals(this)) {
            throw new InvalidInputException("No such answer found for this round.");
        }
        selectedAnswers.put(player, playerAnswer);
    }

    void getReady(Player player) {
        readyPlayers.add(player);
    }
}