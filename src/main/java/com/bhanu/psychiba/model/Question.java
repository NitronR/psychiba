package com.bhanu.psychiba.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

// TODO M-M with content writer
@Entity
@Table(name = "questions")
public class Question extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    
    @NotBlank
    @Getter
    @Setter
    private String questionText;

    @NotBlank
    @Getter
    @Setter
    private String correctAnswer;

}