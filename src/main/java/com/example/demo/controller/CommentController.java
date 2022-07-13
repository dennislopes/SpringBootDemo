package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    
    @GetMapping
    public List<CommentDto> list(Long id){
        if (id == null){
            List<Comment> comments = commentRepository.findAll();       
            return CommentDto.convert(comments);
        } else {
            List<Comment> comments = commentRepository.findByid(id);   
            return CommentDto.convert(comments);
        }
    }
    @PostMapping
    @Transactional
    public ResponseEntity<CommentDto> addComment(@RequestBody @Valid CommentForm form, UriComponentsBuilder uriBuilder){
        Comment comment = form.convert();
        commentRepository.save(comment);

        URI uri = uriBuilder.path("/comments/{id}").buildAndExpand(comment.getId()).toUri();
        return ResponseEntity.created(uri).body(new CommentDto(comment));

    }

    @GetMapping("/{id}")
    public CommentDto detail(@PathVariable Long id){
        Comment comment = commentRepository.getReferenceById(id);
        return new CommentDto(comment);
        } 
        
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody @Valid UpdateCommentForm form){
        Comment comment = form.update(id, commentRepository);
        return ResponseEntity.ok(new CommentDto(comment));

    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<CommentDto> removeComment(@PathVariable Long id){
        commentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
