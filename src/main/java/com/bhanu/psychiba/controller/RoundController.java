package com.bhanu.psychiba.controller;

import java.util.List;

import javax.validation.Valid;

import com.bhanu.psychiba.model.Round;
import com.bhanu.psychiba.repository.RoundRepository;

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

@RestController
@RequestMapping("/api")
public class RoundController {
    @Autowired
    private RoundRepository roundRepository;

    @GetMapping("/rounds")
    public List<Round> getAllRounds() {
        return roundRepository.findAll();
    }

    @PostMapping("/rounds")
    public Round createRound(@Valid @RequestBody Round round) {
        return roundRepository.save(round);
    }

    @GetMapping("/rounds/{id}")
    public Round getRoundById(@PathVariable(value = "id") Long id) throws Exception {
        return roundRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
    }

    @PutMapping("/rounds/{id}")
    public Round updateRound(@PathVariable(value = "id") Long id, @Valid @RequestBody Round round) throws Exception {
        Round p = roundRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        p.setRoundNumber(round.getRoundNumber());
        return roundRepository.save(p);
    }

    @DeleteMapping("/rounds/{id}")
    public ResponseEntity<?> deleteRound(@PathVariable(value = "id") Long id) throws Exception {
        Round p = roundRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        roundRepository.delete(p);
        return ResponseEntity.ok().build();
    }
}