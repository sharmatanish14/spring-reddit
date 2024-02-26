package com.learning.springreddit.controller;

import com.learning.springreddit.dto.CommentDto;
import com.learning.springreddit.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-postid/{id}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.getAllCommentsForPost(id), HttpStatus.OK);
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentDto>> getCommentsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(commentService.getCommentsByUsername(username), HttpStatus.OK);
    }

}
