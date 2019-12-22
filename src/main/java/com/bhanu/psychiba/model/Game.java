package com.bhanu.psychiba.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "games")
public class Game extends Auditable {
    @Getter
    @Setter
    @NonNull
    private int numRounds;

    @Getter
    @Setter
    @NonNull
    private int currentRound = 0;

    @NotNull
    @Getter
    @Setter
    @ManyToMany
    private Map<Player, Stats> playerStats;

    @Getter
    @Setter
    @ManyToMany
    private List<Player> players;

    @NotNull
    @Getter
    @Setter
    private GameMode gameMode;

    @NotNull
    @Getter
    @Setter
    private GameStatus gameStatus = GameStatus.JOINING;

    @ManyToOne
    @NotNull
    @Getter
    @Setter
    private Player leader;

    @Getter
    @Setter
    @NotNull
    @OneToMany(mappedBy = "game")
    private List<Round> rounds;
}