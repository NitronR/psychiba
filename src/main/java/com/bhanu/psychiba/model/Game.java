package com.bhanu.psychiba.model;

import com.bhanu.psychiba.exceptions.InsufficientPlayersException;
import com.bhanu.psychiba.exceptions.InvalidActionForGameStateException;
import com.bhanu.psychiba.exceptions.InvalidInputException;
import com.bhanu.psychiba.utils.Utils;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "games")
public class Game extends Auditable {
    @Getter
    @Setter
    @NotNull
    private int numRounds;

    @NotNull
    @Getter
    @Setter
    private GameMode gameMode;

    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL)
    private Map<Player, Stats> playerStats = new HashMap<>();

    @Getter
    @Setter
    @ManyToMany
    @JsonIdentityReference
    private Set<Player> players = new HashSet<>();

    @Getter
    @Setter
    private GameStatus gameStatus = GameStatus.JOINING;

    @Getter
    @Setter
    @NotNull
    private boolean hasEllen;

    @ManyToOne
    @Getter
    @Setter
    @JsonIdentityReference
    private Player leader;

    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Round> rounds = new ArrayList<>();

    public Game() {

    }

    private Game(Builder builder) {
        setHasEllen(builder.hasEllen);
        setNumRounds(builder.numRounds);
        setGameMode(builder.gameMode);
        setLeader(builder.leader);
        try {
            addPlayer(leader);
        } catch (InvalidActionForGameStateException e) {
            // ignore
        }
    }

    public boolean hasPlayer(Player player) {
        return playerStats.containsKey(player);
    }

    public void addPlayer(Player player) throws InvalidActionForGameStateException {
        if (!gameStatus.equals(GameStatus.JOINING)) {
            throw new InvalidActionForGameStateException("Cannot join game in this game state.");
        }
        if (playerStats.containsKey(player))
            return;
        players.add(player);
        playerStats.put(player, new Stats());
    }

    private void startNewRound() {
        // start game and create first round
        Round round = new Round(this, Utils.getRandomQuestion(gameMode), 1);
        rounds.add(round);
        gameStatus = GameStatus.SUBMITTING_ANSWERS;
    }

    public void start() throws InvalidActionForGameStateException, InsufficientPlayersException {
        // game cannot start if already started
        if (!gameStatus.equals(GameStatus.JOINING)) {
            throw new InvalidActionForGameStateException("Game has already started");
        }

        // at least 2 players to start a game
        if (players.size() < 2) {
            throw new InsufficientPlayersException("At least 2 players should be participating for starting the game");
        }

        startNewRound();
    }

    public void submitAnswer(Player player, String answer) throws InvalidActionForGameStateException {
        if (!gameStatus.equals(GameStatus.SUBMITTING_ANSWERS)) {
            throw new InvalidActionForGameStateException("Not accepting answers.");
        }

        Round round = getCurrentRound();
        round.submitAnswer(player, answer);

        if (round.getSubmittedAnswers().size() == players.size()) {
            gameStatus = GameStatus.SELECTING_ANSWERS;
        }
    }

    Round getCurrentRound() {
        return rounds.get(rounds.size() - 1);
    }

    public void selectAnswer(Player player, String answer) throws InvalidActionForGameStateException, InvalidInputException {
        if (!gameStatus.equals(GameStatus.SELECTING_ANSWERS)) {
            throw new InvalidActionForGameStateException("Not selecting answers at the moment.");
        }
        Round round = getCurrentRound();
        round.selectAnswer(player, answer);

        if (round.getSelectedAnswers().size() == players.size()) {
            if (rounds.size() < numRounds) {
                gameStatus = GameStatus.GETTING_READY;
                // TODO ellen stats here
            } else {
                gameStatus = GameStatus.OVER;
            }
        }
    }

    public void getReady(Player player) {
        Round round = getCurrentRound();
        round.getReady(player);
        if (round.getReadyPlayers().size() == players.size()) {
            startNewRound();
        }
    }

    public GameState getState() {
        return new GameState(this);
    }

    void answeredCorrect(Player player) {
        playerStats.get(player).incCorrectAnswer();
        // options to update player stats
        // Player's stat attribute will be updated once the game is over
        // or right here
        player.getPlayerStats().incCorrectAnswer();
        // or should be moved to the player class
    }

    public static class Builder {
        private int numRounds;
        private GameMode gameMode;
        private boolean hasEllen;
        private Player leader;

        public Builder numRounds(int numRounds) {
            this.numRounds = numRounds;
            return this;
        }

        public Builder gameMode(GameMode gameMode) {
            this.gameMode = gameMode;
            return this;
        }

        public Builder leader(Player leader) {
            this.leader = leader;
            return this;
        }

        public Game build() {
            return new Game(this);
        }

        public Builder hasEllen(boolean hasEllen) {
            this.hasEllen = hasEllen;
            return this;
        }
    }
}