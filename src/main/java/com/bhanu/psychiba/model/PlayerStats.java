package com.bhanu.psychiba.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "player_stats")
public class PlayerStats extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Long numPlayed;

    @Getter
    @Setter
    private Long numPsyched;

    @Getter
    @Setter
    private Long numPsychedBy;
}