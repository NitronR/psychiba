package com.bhanu.psychiba.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

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
    private int currentRound = 0;

    @Getter
    @Setter
    @ManyToMany
    private Map<Player, Stats> playerStats;

    @Getter
    @Setter
    @ManyToMany
    private List<Player> players = new ArrayList<>();

    @Getter
    @Setter
    private GameStatus gameStatus = GameStatus.JOINING;
    
    @Getter
    @Setter
    @NotNull
    private int numEllens;

    @ManyToOne
    @Getter
    @Setter
    private Player leader;

    @Getter
    @Setter
    @OneToMany(mappedBy = "game")
    private List<Round> rounds;
}