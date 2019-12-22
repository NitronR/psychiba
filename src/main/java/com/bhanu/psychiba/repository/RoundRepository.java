package com.bhanu.psychiba.repository;

import com.bhanu.psychiba.model.Round;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

}