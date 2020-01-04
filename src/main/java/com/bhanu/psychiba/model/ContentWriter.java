package com.bhanu.psychiba.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "content_writers")
public class ContentWriter extends Employee {
    @ManyToMany
    @Getter
    @Setter
    @JsonIdentityReference
    private List<Question> editedQuestions = new ArrayList<>();
}