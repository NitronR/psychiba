package com.bhanu.psychiba.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "selected_answers")
public class SelectedAnswer extends Auditable {
    @Getter
    @Setter
    private PlayerAnswer playerAnswer;

    @Getter
    @Setter
    @NotNull
    private AnswerType answerType;

    @Getter
    @Setter
    @NotNull
    @JsonBackReference
    private Round round;

    public SelectedAnswer() {

    }

    private SelectedAnswer(Builder builder) {
        setAnswerType(builder.answerType);
        setPlayerAnswer(builder.playerAnswer);
        setRound(builder.round);
    }

    public static class Builder {
        private PlayerAnswer playerAnswer;

        @NotNull
        private AnswerType answerType;

        @NotNull
        private Round round;

        public Builder playerAnswer(PlayerAnswer playerAnswer) {
            this.playerAnswer = playerAnswer;
            return this;
        }

        public Builder answerType(AnswerType answerType) {
            this.answerType = answerType;
            return this;
        }

        public Builder round(Round round) {
            this.round = round;
            return this;
        }

        public SelectedAnswer build() {
            return new SelectedAnswer(this);
        }
    }
}
