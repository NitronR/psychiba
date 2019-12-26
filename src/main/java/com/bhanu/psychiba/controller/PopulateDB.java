package com.bhanu.psychiba.controller;

import java.io.IOException;
import java.util.Map;

import com.bhanu.psychiba.Constants;
import com.bhanu.psychiba.utils.Utils;
import com.bhanu.psychiba.model.GameMode;
import com.bhanu.psychiba.model.Player;
import com.bhanu.psychiba.model.Question;
import com.bhanu.psychiba.repository.PlayerRepository;
import com.bhanu.psychiba.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/dev")
@RestController
public class PopulateDB {
    @Autowired
    private QuestionRepository qRepository;
    @Autowired
    private PlayerRepository pRepository;

    @GetMapping("/add-questions-form-file")
    public void addQuestionsFromFile() throws IOException {
        qRepository.deleteAll();
        for (Map.Entry<String, GameMode> fileMode : Constants.qaFiles.entrySet()) {
            int qnum = 0;
            String fileName = fileMode.getKey();
            GameMode gameMode = fileMode.getValue();
            for (Pair<String, String> questionAnswer : Utils.readQAFile(fileName)) {
                Question question = new Question();
                question.setQuestionText(questionAnswer.getFirst());
                question.setCorrectAnswer(questionAnswer.getSecond());
                question.setGameMode(gameMode);
                qRepository.save(question);
                qnum++;
                if (qnum > Constants.MAX_QUESTIONS_READ) {
                    break;
                }
            }
        }
    }

    @GetMapping("/add-dummy-players")
    public void addDummyPlayers() {
        pRepository.deleteAll();

        Player luffy = new Player();
        luffy.setName("Son of Monkey D. Dragon");

        Player zoro = new Player();
        zoro.setName("1 billion worlds");

        pRepository.save(luffy);
        pRepository.save(zoro);
    }
}