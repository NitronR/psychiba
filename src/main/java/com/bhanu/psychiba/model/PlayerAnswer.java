package com.bhanu.psychiba.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.bhanu.psychiba.Constants;

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

    @Getter
    @Setter
    private Player player;

    @Getter
    @Setter
    private Round round;
}