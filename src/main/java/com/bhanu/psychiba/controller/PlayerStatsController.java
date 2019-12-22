package com.bhanu.psychiba.controller;

import java.util.List;

import javax.validation.Valid;

import com.bhanu.psychiba.model.Stats;
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
    public List<Stats> getAllPlayerStats() {
        return statsRepository.findAll();
    }

    @PostMapping("/player_stats")
    public Stats createPlayerStats(@Valid @RequestBody Stats playerStats) {
        return statsRepository.save(playerStats);
    }

    @GetMapping("/player_stats/{id}")
    public Stats getPlayerStatsById(@PathVariable(value = "id") Long id) throws Exception {
        return statsRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
    }

    @PutMapping("/player_stats/{id}")
    public Stats updatePlayerStats(@PathVariable(value = "id") Long id, @Valid @RequestBody Stats playerStats)
            throws Exception {
        Stats p = statsRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        p.setGotPsychedCount(playerStats.getGotPsychedCount());
        p.setPsychedOthersCount(playerStats.getPsychedOthersCount());
        p.setCorrectAnswers(playerStats.getCorrectAnswers());
        return statsRepository.save(p);
    }

    @DeleteMapping("/player_stats/{id}")
    public ResponseEntity<?> deletePlayerStats(@PathVariable(value = "id") Long id) throws Exception {
        Stats p = statsRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        statsRepository.delete(p);
        return ResponseEntity.ok().build();
    }
}