package com.bhanu.psychiba.controller;

import javax.validation.Valid;

import com.bhanu.psychiba.forms.InviteForm;
import com.bhanu.psychiba.model.Game;
import com.bhanu.psychiba.model.GameMode;
import com.bhanu.psychiba.model.Player;
import com.bhanu.psychiba.repository.GameRepository;
import com.bhanu.psychiba.repository.PlayerRepository;
import com.bhanu.psychiba.repository.QuestionRepository;
import com.bhanu.psychiba.repository.RoundRepository;
import com.bhanu.psychiba.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameManager {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    QuestionRepository quesRepository;
    @Autowired
    RoundRepository roundRepository;

    @GetMapping("/create/{pid}/{gm}/{nr}")
    public String createGame(@PathVariable(value = "pid") Long playerId, @PathVariable(value = "gm") int gm,
            @PathVariable(value = "nr") int numRounds) {
        Player leader = playerRepository.findById(playerId).orElseThrow();
        // TODO interpret game mode
        GameMode gameMode = GameMode.IS_THAT_A_FACT;

        Game game = new Game();
        game.setLeader(leader);
        game.setNumRounds(numRounds);
        game.setGameMode(gameMode);

        game.getPlayers().add(leader);

        gameRepository.save(game);

        return game.getId().toString() + "-" + Utils.getGameCode(game.getId());
    }

    @GetMapping("/join/{pid}/{gc}")
    public String joinGame(@PathVariable(value = "pid") Long pid, @PathVariable(value = "gc") String gameCode){
        Game game = gameRepository.findById(Utils.getGameIdFromGameCode(gameCode)).orElseThrow();
        Player player = playerRepository.findById(pid).orElseThrow();

        game.getPlayers().add(player);

        gameRepository.save(game);

        return "success";
    }

    @PostMapping("/send_invites")
    public ResponseEntity<?> sendInvites(@Valid @RequestBody InviteForm inviteForm) throws Exception {
        // TODO get player from session
        Player player = playerRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).get(0);

        // Check if given game's leader is current session player
        Game game = gameRepository.findById(inviteForm.getGameId())
                .orElseThrow(() -> new Exception("Something went wrong."));

        // if it is not then it is an invalid request.
        if (game.getLeader().getId() != player.getId()) {
            return ResponseEntity.badRequest().build();
        }

        String gameCode = Utils.getGameCode(inviteForm.getGameId());
        Utils.sendInvites(inviteForm.getEmails(), player.getName(), gameCode);

        return ResponseEntity.ok().build();
    }
}