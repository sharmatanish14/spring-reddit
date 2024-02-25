package com.learning.springreddit.service;

import com.learning.springreddit.dto.PostRequest;
import com.learning.springreddit.dto.PostResponse;
import com.learning.springreddit.exception.SpringRedditException;
import com.learning.springreddit.exception.SubredditNotFoundException;
import com.learning.springreddit.mapper.PostMapper;
import com.learning.springreddit.model.Post;
import com.learning.springreddit.model.Subreddit;
import com.learning.springreddit.model.User;
import com.learning.springreddit.respository.PostRepository;
import com.learning.springreddit.respository.SubredditRepository;
import com.learning.springreddit.respository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).
                orElseThrow(() -> new SpringRedditException("No subreddit found for subreddit name: " + postRequest.getSubredditName()));
        User currentUser = authService.getCurrentUser();
        Post post = postMapper.map(postRequest, subreddit, currentUser);
        postRepository.save(post);

    }

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().
                map(postMapper::mapToDto).
                collect(Collectors.toList());
    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new SpringRedditException("Post not found for post id : " + id));
        return postMapper.mapToDto(post);
    }

    public List<PostResponse> getPostsBySubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SubredditNotFoundException("No subreddit found for ID : " + id));
        List<Post> postList = postRepository.findBySubreddit(subreddit);
        return postList.stream().map(postMapper::mapToDto).collect(Collectors.toList());

    }


    public List<PostResponse> getPostsByUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        List<Post> postList = postRepository.findByUser(user);
        return postList.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }
}
