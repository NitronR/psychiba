package com.bhanu.psychiba.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Table(name = "players")
public class Player extends Auditable {

    @NotBlank
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @URL
    private String psychFaceURL;

    @Getter
    @Setter
    @URL
    private String picURL;

    @Getter
    @Setter
    @OneToOne
    private Stats playerStats;

    @ManyToMany(mappedBy = "players")
    @Getter
    @Setter
    private List<Game> games;
}