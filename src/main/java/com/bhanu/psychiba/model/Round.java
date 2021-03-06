package com.bhanu.psychiba.model;

import com.bhanu.psychiba.exceptions.InvalidActionForGameStateException;
import com.bhanu.psychiba.exceptions.InvalidInputException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JsonManagedReference
    private Map<Player, PlayerAnswer> submittedAnswers = new HashMap<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @Getter
    @Setter
    @JsonManagedReference
    private Map<Player, SelectedAnswer> selectedAnswers = new HashMap<>();

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
        // player can only submit a single time
        if (submittedAnswers.containsKey(player)) {
            throw new InvalidActionForGameStateException("You have already submitted.");
        }
        // player cannot submit an answer that anybody else submitted or is the correct answer
        if (question.isCorrectAnswer(answer) || playerAnswerExist(answer)) {
            // TODO also check if it is an ellen answer
            throw new InvalidActionForGameStateException("Someone else has already submitted this answer.");
        }

        PlayerAnswer playerAnswer = new PlayerAnswer();
        submittedAnswers.put(player, playerAnswer);
    }

    private boolean playerAnswerExist(String answer) {
        return getPlayerAnswer(answer) != null;
    }

    private PlayerAnswer getPlayerAnswer(String answer) {
        for (PlayerAnswer playerAnswer : submittedAnswers.values()) {
            if (playerAnswer.getAnswer().equals(answer)) return playerAnswer;
        }

        return null;
    }

    void selectAnswer(Player player, String answer) throws InvalidInputException {
        SelectedAnswer.Builder selAnswerBuilder = new SelectedAnswer.Builder();
        selAnswerBuilder.round(this);

        // if the answer is correct
        if (question.isCorrectAnswer(answer)) {
            selAnswerBuilder.answerType(AnswerType.CORRECT);
            selectedAnswers.put(player, selAnswerBuilder.build());
            return;
        }
        // TODO also check if it is an ellen answer
        if (playerAnswerExist(answer)) {
            // if it is a player answer, add to selected answer
            // TODO update psych stats
            selAnswerBuilder.answerType(AnswerType.PLAYER);
            selAnswerBuilder.playerAnswer(getPlayerAnswer(answer));
            selectedAnswers.put(player, selAnswerBuilder.build());
            return;
        }
        throw new InvalidInputException("No such answer found for this round.");
    }

    void getReady(Player player) {
        readyPlayers.add(player);
    }

    List<PlayerAnswer> getSelectedPlayerAnswers() {
        List<PlayerAnswer> playerAnswers = new ArrayList<>();

        for (SelectedAnswer selectedAnswer : selectedAnswers.values()) {
            if (selectedAnswer.getAnswerType().equals(AnswerType.PLAYER)) {
                playerAnswers.add(selectedAnswer.getPlayerAnswer());
            }
        }
        return playerAnswers;
    }

    Map<Player, Stats> calculateStats() {
        Map<Player, Stats> roundStats = new HashMap<>();

        // add entries for each player
        for (Player player : selectedAnswers.keySet()) {
            roundStats.put(player, new Stats());
        }

        // populate stats
        for (Map.Entry<Player, SelectedAnswer> selectedAnswerEntry : selectedAnswers.entrySet()) {
            Player player = selectedAnswerEntry.getKey();
            SelectedAnswer selectedAnswer = selectedAnswerEntry.getValue();

            switch (selectedAnswer.getAnswerType()) {
                case CORRECT:
                    roundStats.get(player).incCorrectAnswer();
                    break;
                case PLAYER:
                    // increment got psyched count of player who
                    // selected another player's submitted answer
                    roundStats.get(player).incGotPsychedCount();

                    // increment psychOther count of the player who's
                    // answer psyched this player successfully
                    Player psycher = selectedAnswer.getPlayerAnswer().getPlayer();
                    roundStats.get(psycher).incPsychOthersCount();
                    break;
                case ELLEN:
                    // TODO decide if ellen psychs are counted
            }
        }

        return roundStats;
    }
}