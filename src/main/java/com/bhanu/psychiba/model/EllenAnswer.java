package com.bhanu.psychiba.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bhanu.psychiba.Constants;

import lombok.Getter;
import lombok.Setter;

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
    private Question question;
}