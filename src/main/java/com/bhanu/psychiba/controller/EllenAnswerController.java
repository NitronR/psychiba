package com.bhanu.psychiba.controller;

import java.util.List;

import javax.validation.Valid;

import com.bhanu.psychiba.model.EllenAnswer;
import com.bhanu.psychiba.repository.EllenAnswerRepository;

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
public class EllenAnswerController {
    @Autowired
    private EllenAnswerRepository ellenAnswerRepository;

    @GetMapping("/ellen_answers")
    public List<EllenAnswer> getAllEllenAnswers() {
        return ellenAnswerRepository.findAll();
    }

    @PostMapping("/ellen_answers")
    public EllenAnswer createEllenAnswer(@Valid @RequestBody EllenAnswer ellenAnswer) {
        return ellenAnswerRepository.save(ellenAnswer);
    }

    @GetMapping("/ellen_answers/{id}")
    public EllenAnswer getEllenAnswerById(@PathVariable(value = "id") Long id) throws Exception {
        return ellenAnswerRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
    }

    @PutMapping("/ellen_answers/{id}")
    public EllenAnswer updateEllenAnswer(@PathVariable(value = "id") Long id,
            @Valid @RequestBody EllenAnswer ellenAnswer) throws Exception {
        EllenAnswer p = ellenAnswerRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        p.setAnswer(ellenAnswer.getAnswer());
        p.setVoteCount(ellenAnswer.getVoteCount());
        return ellenAnswerRepository.save(p);
    }

    @DeleteMapping("/ellen_answers/{id}")
    public ResponseEntity<?> deleteEllenAnswer(@PathVariable(value = "id") Long id) throws Exception {
        EllenAnswer p = ellenAnswerRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        ellenAnswerRepository.delete(p);
        return ResponseEntity.ok().build();
    }
}