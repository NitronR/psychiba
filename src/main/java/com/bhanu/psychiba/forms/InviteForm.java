package com.bhanu.psychiba.forms;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class InviteForm {
    @NotNull
    @Getter
    @Setter
    private Long gameId;

    @Getter
    @Setter
    List<String> emails;
}