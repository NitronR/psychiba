package com.bhanu.psychiba.repository;

import com.bhanu.psychiba.model.Stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatsRepository extends JpaRepository<Stats, Long> {

}