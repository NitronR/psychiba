package com.bhanu.psychiba.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class GameState {
    @Getter
    @Setter
    private Round currentRound;

    @Getter
    @Setter
    private Map<Player, Stats> playerStats;

    @Getter
    @Setter
    private GameStatus gameStatus;

    GameState(Game game) {
        this.currentRound = game.getCurrentRound();
        this.playerStats = game.getPlayerStats();
        this.gameStatus = game.getGameStatus();
        // TODO add ellen answer to game state
    }
}
