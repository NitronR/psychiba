package com.bhanu.psychiba.controller;

import javax.validation.Valid;

import com.bhanu.psychiba.model.Game;
import com.bhanu.psychiba.model.Player;
import com.bhanu.psychiba.repository.GameRepository;
import com.bhanu.psychiba.repository.PlayerRepository;
import com.bhanu.psychiba.reqbody.GameForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev")
public class GameManager {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    GameRepository gameRepository;

    @PostMapping("/create_game")
    // public Game createGame() {
    public Game createGame(@Valid @RequestBody GameForm gameForm) {
        Game game = new Game();

        game.setNumRounds(gameForm.getNumRounds());
        game.setGameMode(gameForm.getGameMode());

        // TODO get player id from session and then get player, insted of first using
        // first player in table
        Player leader = playerRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).get(0);
        game.setLeader(leader);

        gameRepository.save(game);

        return game;
    }
}