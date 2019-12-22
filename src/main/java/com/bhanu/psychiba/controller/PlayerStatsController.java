package com.bhanu.psychiba.controller;

import java.util.List;

import javax.validation.Valid;

import com.bhanu.psychiba.model.PlayerStats;
import com.bhanu.psychiba.repository.PlayerStatsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO one to one with player
@RestController
@RequestMapping("/api")
public class PlayerStatsController {
    @Autowired
    private PlayerStatsRepository statsRepository;

    // not sure if these are required
    @GetMapping("/player_stats")
    public List<PlayerStats> getAllPlayerStats() {
        return statsRepository.findAll();
    }

    @PostMapping("/player_stats")
    public PlayerStats createPlayerStats(@Valid @RequestBody PlayerStats playerStats) {
        return statsRepository.save(playerStats);
    }

    @GetMapping("/player_stats/{id}")
    public PlayerStats getPlayerStatsById(@PathVariable(value = "id") Long id) throws Exception {
        return statsRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
    }

    @PutMapping("/player_stats/{id}")
    public PlayerStats updatePlayerStats(@PathVariable(value = "id") Long id,
            @Valid @RequestBody PlayerStats playerStats) throws Exception {
        PlayerStats p = statsRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        p.setNumPlayed(playerStats.getNumPlayed());
        p.setNumPsyched(playerStats.getNumPsyched());
        p.setNumPsychedBy(playerStats.getNumPsychedBy());
        return statsRepository.save(p);
    }

    @DeleteMapping("/player_stats/{id}")
    public ResponseEntity<?> deletePlayerStats(@PathVariable(value = "id") Long id) throws Exception {
        PlayerStats p = statsRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        statsRepository.delete(p);
        return ResponseEntity.ok().build();
    }
}