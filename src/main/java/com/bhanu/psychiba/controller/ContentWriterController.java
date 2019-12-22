package com.bhanu.psychiba.controller;

import java.util.List;

import javax.validation.Valid;

import com.bhanu.psychiba.model.ContentWriter;
import com.bhanu.psychiba.repository.ContentWriterRepository;

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
public class ContentWriterController {
    @Autowired
    private ContentWriterRepository cwRepository;

    @GetMapping("/content_writers")
    public List<ContentWriter> getAllContentWriters() {
        return cwRepository.findAll();
    }

    @PostMapping("/content_writers")
    public ContentWriter createContentWriter(@Valid @RequestBody ContentWriter contentWriter) {
        return cwRepository.save(contentWriter);
    }

    @GetMapping("/content_writers/{id}")
    public ContentWriter getContentWriterById(@PathVariable(value = "id") Long id) throws Exception {
        return cwRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
    }

    @PutMapping("/content_writers/{id}")
    public ContentWriter updateContentWriter(@PathVariable(value = "id") Long id,
            @Valid @RequestBody ContentWriter contentWriter) throws Exception {
        ContentWriter p = cwRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        p.setName(contentWriter.getName());
        return cwRepository.save(p);
    }

    @DeleteMapping("/content_writers/{id}")
    public ResponseEntity<?> deleteContentWriter(@PathVariable(value = "id") Long id) throws Exception {
        ContentWriter p = cwRepository.findById(id).orElseThrow(() -> new Exception("Something went wrong."));
        cwRepository.delete(p);
        return ResponseEntity.ok().build();
    }
}