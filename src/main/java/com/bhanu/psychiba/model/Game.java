package com.bhanu.psychiba.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

// TODO one to one with game leader
// TODO 1 to many to round for game rounds
// TODO 1 to many to player for participants
@Entity
@Table(name = "games")
public class Game extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Enumerated(EnumType.ORDINAL)
    private GameMode gameMode;

    // for enum, we have specific choices of 5, 10, 20, infinity
    // integer is not suitable I think?
    // for enum, what would be the most appropriate method?

    // I think game state enum would be more appropriate than is_over
    // Like JOINING, IN_PROGRESS, OVER
}

// not sure where to place this
enum GameMode {
    TRUTH_COMES_OUT, MOVIE_BLUFF, WORD_UP, IS_THAT_A_FACT
}