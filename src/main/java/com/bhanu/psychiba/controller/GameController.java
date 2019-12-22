package com.bhanu.psychiba.controller;

import java.util.List;

import javax.validation.Valid;

import com.bhanu.psychiba.model.Game;
import com.bhanu.psychiba.repository.GameRepository;

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
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @PostMapping("/games")
    public Game createGame(@Valid @RequestBody Game game) {
        return gameRepository.save(game);
    }

    @GetMapping("/games/{id}")
    public Game getGameById(@PathVariable(value = "id") Long id) throws Exception {
        return gameRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
    }

    @PutMapping("/games/{id}")
    public Game updateGame(@PathVariable(value = "id") Long id, @Valid @RequestBody Game game) throws Exception {
        Game p = gameRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        p.setGameMode(game.getGameMode());
        return gameRepository.save(p);
    }

    @DeleteMapping("/games/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable(value = "id") Long id) throws Exception {
        Game p = gameRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        gameRepository.delete(p);
        return ResponseEntity.ok().build();
    }
}