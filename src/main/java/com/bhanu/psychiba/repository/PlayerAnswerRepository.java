package com.bhanu.psychiba.repository;

import com.bhanu.psychiba.model.PlayerAnswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerAnswerRepository extends JpaRepository<PlayerAnswer, Long> {

}