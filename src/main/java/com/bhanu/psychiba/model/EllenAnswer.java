package com.bhanu.psychiba.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

// TODO M ellen answer to 1 question 
@Entity
@Table(name = "ellen_answers")
public class EllenAnswer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @NotBlank
    @Getter
    @Setter
    private String answer;

    @Getter
    @Setter
    private Long voteCount;
}