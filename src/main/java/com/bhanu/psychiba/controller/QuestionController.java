package com.bhanu.psychiba.controller;

import java.util.List;

import javax.validation.Valid;

import com.bhanu.psychiba.model.Question;
import com.bhanu.psychiba.repository.QuestionRepository;

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
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/questions")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @PostMapping("/questions")
    public Question createQuestion(@Valid @RequestBody Question question) {
        return questionRepository.save(question);
    }

    @GetMapping("/questions/{id}")
    public Question getQuestionById(@PathVariable(value = "id") Long id) throws Exception {
        return questionRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
    }

    @PutMapping("/questions/{id}")
    public Question updateQuestion(@PathVariable(value = "id") Long id, @Valid @RequestBody Question question)
            throws Exception {
        Question p = questionRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        p.setQuestionText(question.getQuestionText());
        p.setCorrectAnswer(question.getCorrectAnswer());
        return questionRepository.save(p);
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable(value = "id") Long id) throws Exception {
        Question p = questionRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        questionRepository.delete(p);
        return ResponseEntity.ok().build();
    }
}