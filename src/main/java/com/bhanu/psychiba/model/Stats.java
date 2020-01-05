package com.bhanu.psychiba.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stats")
public class Stats extends Auditable {

    @Getter
    @Setter
    private Long correctAnswers = 0L;
    @Getter
    @Setter
    private Long gotPsychedCount = 0L;
    @Getter
    @Setter
    private Long psychedOthersCount = 0L;

    void updateStats(Stats stats) {
        correctAnswers += stats.correctAnswers;
        gotPsychedCount += stats.gotPsychedCount;
        psychedOthersCount += stats.psychedOthersCount;
    }

    void incCorrectAnswer() {
        correctAnswers++;
    }

    void incGotPsychedCount() {
        gotPsychedCount++;
    }

    void incPsychOthersCount() {
        psychedOthersCount++;
    }
}