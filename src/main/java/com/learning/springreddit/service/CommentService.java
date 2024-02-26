package com.learning.springreddit.service;

import com.learning.springreddit.dto.CommentDto;
import com.learning.springreddit.exception.SpringRedditException;
import com.learning.springreddit.mapper.CommentMapper;
import com.learning.springreddit.model.Comment;
import com.learning.springreddit.model.NotificationEmail;
import com.learning.springreddit.model.Post;
import com.learning.springreddit.model.User;
import com.learning.springreddit.respository.CommentRepository;
import com.learning.springreddit.respository.PostRepository;
import com.learning.springreddit.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;


    public void createComment(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new SpringRedditException("No post found for post id: " + commentDto.getPostId()));
        User user = authService.getCurrentUser();
        Comment comment = commentMapper.map(commentDto, post, user);
        commentRepository.save(comment);

        String message = mailContentBuilder.build(user.getUsername() + " posted a comment on your post");
        sendCommentNotification(message, user);

    }

    public List<CommentDto> getAllCommentsForPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new SpringRedditException("Post not found for ID: " + id));
        List<Comment> commentList = commentRepository.findByPost(post);
        List<CommentDto> commentDtoList = commentList.stream().map(commentMapper::mapToDto).collect(Collectors.toList());
        return commentDtoList;
    }

    public List<CommentDto> getCommentsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found for username : " + username));
        List<Comment> commentList = commentRepository.findByUser(user);
        List<CommentDto> commentDtoList = commentList.stream().map(commentMapper::mapToDto).collect(Collectors.toList());
        return commentDtoList;

    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }
}
