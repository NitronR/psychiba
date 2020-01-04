package com.bhanu.psychiba.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

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
    @OneToOne(cascade = CascadeType.ALL)
    private Stats playerStats = new Stats();

    @ManyToMany(mappedBy = "players")
    @Getter
    @Setter
    @JsonIdentityReference
    private List<Game> games;
    
    public Player(Builder builder){
        setName(builder.name);
        setPicURL(builder.picURL);
        setPsychFaceURL(builder.psychFaceURL);
    }

    public Player(){}

    public static class Builder {
        private String name;
        private String psychFaceURL;
        private String picURL;

        public Player build(){
            return new Player(this);
        }

        public Builder name(@NotBlank String name) {
            this.name = name;
            return this;
        }

        public Builder psychFaceURL(@URL String psychFaceURL) {
            this.psychFaceURL = psychFaceURL;
            return this;
        }

        public Builder picURL(@URL String picURL) {
            this.picURL = picURL;
            return this;
        }
    }
}