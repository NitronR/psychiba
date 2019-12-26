package com.bhanu.psychiba.forms;

import javax.validation.constraints.NotNull;

import com.bhanu.psychiba.model.GameMode;

import lombok.Getter;
import lombok.Setter;

public class GameForm {
    @NotNull
    @Getter
    @Setter
    private int numRounds;

    @NotNull
    @Getter
    @Setter
    private GameMode gameMode;
}