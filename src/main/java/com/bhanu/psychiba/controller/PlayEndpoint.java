package com.bhanu.psychiba.controller;

import com.bhanu.psychiba.exceptions.IllegalGameException;
import com.bhanu.psychiba.exceptions.InsufficientPlayersException;
import com.bhanu.psychiba.exceptions.InvalidActionForGameStateException;
import com.bhanu.psychiba.exceptions.InvalidInputException;
import com.bhanu.psychiba.forms.InviteForm;
import com.bhanu.psychiba.model.*;
import com.bhanu.psychiba.repository.*;
import com.bhanu.psychiba.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/game")
public class PlayEndpoint {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private QuestionRepository quesRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private PlayerAnswerRepository playerAnswerRepository;

    @GetMapping("/create/{pid}/{gm}/{nr}/{has_ellen}")
    public String createGame(@PathVariable(value = "pid") Long playerId, @PathVariable(value = "gm") int gm,
                             @PathVariable(value = "nr") int numRounds, @PathVariable(value = "has_ellen") int hasEllen) {
        Player leader = playerRepository.findById(playerId).orElseThrow();
        GameMode gameMode = GameMode.fromValue(gm);

        Game game = new Game.Builder()
                .leader(leader)
                .numRounds(numRounds)
                .gameMode(gameMode)
                .hasEllen(hasEllen == 1)
                .build();

        gameRepository.save(game);

        return "Created game:" + game.getId().toString() + ". Code:" + Utils.getGameCode(game.getId());
    }

    @GetMapping("/join/{pid}/{gc}")
    public String joinGame(@PathVariable(value = "pid") Long pid, @PathVariable(value = "gc") String gameCode)
            throws InvalidActionForGameStateException {
        Game game = gameRepository.findById(Utils.getGameIdFromGameCode(gameCode)).orElseThrow();

        Player player = playerRepository.findById(pid).orElseThrow();
        game.addPlayer(player);
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

    @GetMapping("/start/{pid}/{gid}")
    public String startGame(@PathVariable(value = "pid") Long pid, @PathVariable(value = "gid") Long gameId)
            throws IllegalGameException, InvalidActionForGameStateException, InsufficientPlayersException {
        Player player = playerRepository.findById(pid).orElseThrow();
        Game game = gameRepository.findById(gameId).orElseThrow();

        // only leader can start the game
        if (!game.getLeader().equals(player)) {
            throw new IllegalGameException("Only the leader can start the game");
        }

        game.start();
        gameRepository.save(game);

        return "Game started.";
    }

    @GetMapping("/submit_answer/{pid}/{gid}/{answer}")
    public String submitAnswer(@PathVariable(value = "pid") Long pid, @PathVariable(value = "gid") Long gameId,
                               @PathVariable(value = "answer") String answer) throws IllegalGameException, InvalidActionForGameStateException {
        Player player = playerRepository.findById(pid).orElseThrow();
        Game game = gameRepository.findById(gameId).orElseThrow();

        // player not in game
        if (!game.hasPlayer(player)) {
            throw new IllegalGameException("Only the leader can start the game");
        }

        game.submitAnswer(player, answer);
        gameRepository.save(game);

        return "submitted answer";
    }

    @GetMapping("/select_answer/{pid}/{gid}/{paid}")
    public String selectAnswer(@PathVariable(value = "pid") Long pid, @PathVariable(value = "gid") Long gameId,
                               @PathVariable(value = "paid") Long playerAnswerId) throws InvalidActionForGameStateException, InvalidInputException {
        Player player = playerRepository.findById(pid).orElseThrow();
        Game game = gameRepository.findById(gameId).orElseThrow();
        PlayerAnswer playerAnswer = playerAnswerRepository.findById(playerAnswerId).orElseThrow();

        game.selectAnswer(player, playerAnswer);
        gameRepository.save(game);
        return "selected answer";
    }

    @GetMapping("/get_ready/{pid}/{gid}")
    public String getReady(@PathVariable(value = "pid") Long pid, @PathVariable(value = "gid") Long gameId) {

        Game game = gameRepository.findById(gameId).orElseThrow();
        Player player = playerRepository.findById(pid).orElseThrow();

        game.getReady(player);
        gameRepository.save(game);

        return "success";
    }

    @GetMapping("/get_game_state/{gid}")
    public  String gameState(@PathVariable(value = "gid") Long gameId){
        Game game = gameRepository.findById(gameId).orElseThrow();
        return game.getState();
    }

}