package com.bhanu.psychiba.model;

import com.bhanu.psychiba.Constants;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "ellen_answers")
public class EllenAnswer extends Auditable {

    @NotBlank
    @Getter
    @Setter
    @Column(length = Constants.MAX_ANSWER_LENGTH)
    private String answer;

    @NotNull
    @Getter
    @Setter
    private Long voteCount = 0L;

    @NotNull
    @ManyToOne
    @Getter
    @Setter
    @JsonBackReference
    private Question question;

    // TODO cumulative in local db and update in job queue, in a separate server
    static void addEllenAnswers(Game game) {
        Map<Pair<Question, String>, Integer> questionAnswers = new HashMap<>();

        // get question answers count of a game
        for (Round round : game.getRounds()) {
            for (PlayerAnswer playerAnswer : round.getSelectedPlayerAnswers()) {
                Pair<Question, String> questionAnswer = Pair.of(round.getQuestion(), playerAnswer.getAnswer());

                // increment count of question-answer pair
                questionAnswers.put(questionAnswer, questionAnswers.getOrDefault(questionAnswer, 0) + 1);
            }
        }

        // TODO update ellen answers
    }
}