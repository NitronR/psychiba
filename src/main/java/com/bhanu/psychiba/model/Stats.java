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
    private Long correctAnswers;
    @Getter
    @Setter
    private Long gotPsychedCount = 0L;
    @Getter
    @Setter
    private Long psychedOthersCount = 0L;

}