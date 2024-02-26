package com.learning.springreddit.service;

import com.learning.springreddit.dto.VoteDto;
import com.learning.springreddit.exception.SpringRedditException;
import com.learning.springreddit.model.Post;
import com.learning.springreddit.model.User;
import com.learning.springreddit.model.Vote;
import com.learning.springreddit.model.VoteType;
import com.learning.springreddit.respository.PostRepository;
import com.learning.springreddit.respository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(() -> new SpringRedditException("No post found for post ID: " + voteDto.getPostId()));
        User currentUser = authService.getCurrentUser();

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, currentUser);

        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already " + voteDto.getVoteType() + " this post");
        }

        if (voteDto.getVoteType().equals(VoteType.UPVOTE)) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        Vote vote = mapToVote(voteDto, post, currentUser);

        voteRepository.save(vote);
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post, User currentUser) {
        Vote vote = Vote.builder().voteType(voteDto.getVoteType())
                .post(post)
                .user(currentUser)
                .build();
        return vote;
    }
}
