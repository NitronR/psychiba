package com.bhanu.psychiba.repository;

import java.util.List;

import com.bhanu.psychiba.model.GameMode;
import com.bhanu.psychiba.model.Question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    public List<Question> findByGameMode(GameMode gameMode);
}