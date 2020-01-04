package com.bhanu.psychiba.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bhanu.psychiba.Constants;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "questions")
public class Question extends Auditable {

    @NotBlank
    @Getter
    @Setter
    @Column(length = Constants.MAX_QUESTION_LENGTH)
    private String questionText;

    @NotBlank
    @Getter
    @Setter
    @Column(length = Constants.MAX_ANSWER_LENGTH)
    private String correctAnswer;

    @Getter
    @Setter
    @NotNull
    private GameMode gameMode;

    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "question")
    private List<EllenAnswer> ellenAnswers = new ArrayList<>();

    private Question(Builder builder) {
        setCorrectAnswer(builder.correctAnswer);
        setGameMode(builder.gameMode);
        setQuestionText(builder.questionText);
    }

    public Question() {
    }

    public static class Builder {
        @NotBlank
        private String questionText;

        @NotBlank
        private String correctAnswer;

        @NotNull
        private GameMode gameMode;

        public Builder questionText(String questionText) {
            this.questionText = questionText;
            return this;
        }

        public Builder correctAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
            return this;
        }

        public Builder gameMode(GameMode gameMode) {
            this.gameMode = gameMode;
            return this;
        }

        public Question build() {
            return new Question(this);
        }
    }

}