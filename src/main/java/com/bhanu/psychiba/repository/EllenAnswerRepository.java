package com.bhanu.psychiba.repository;

import com.bhanu.psychiba.model.EllenAnswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EllenAnswerRepository extends JpaRepository<EllenAnswer, Long> {

}