package com.bhanu.psychiba.model;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rounds")
public class Round extends Auditable {

    @ManyToOne
    @Getter
    @Setter
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
    private Map<Player, PlayerAnswer> playerAnswers;
}