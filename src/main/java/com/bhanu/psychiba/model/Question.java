package com.bhanu.psychiba.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bhanu.psychiba.Constants;

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
    @OneToMany(mappedBy = "question")
    private List<EllenAnswer> ellenAnswers;

}