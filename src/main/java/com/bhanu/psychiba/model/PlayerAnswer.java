package com.bhanu.psychiba.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bhanu.psychiba.Constants;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "player_answers")
public class PlayerAnswer extends Auditable {
    @Getter
    @Setter
    @NotBlank
    @Column(length = Constants.MAX_ANSWER_LENGTH)
    private String answer;

    public PlayerAnswer(){}

    public PlayerAnswer(@NotBlank String answer, @NotNull Player player, Round round) {
        this.answer = answer;
        this.player = player;
        this.round = round;
    }

    @Getter
    @Setter
    @NotNull
    private Player player;

    @Getter
    @Setter
    @JsonBackReference
    private Round round;
}