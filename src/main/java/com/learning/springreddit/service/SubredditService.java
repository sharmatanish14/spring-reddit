package com.learning.springreddit.service;

import com.learning.springreddit.dto.SubredditDto;
import com.learning.springreddit.exception.SpringRedditException;
import com.learning.springreddit.mapper.SubredditMapper;
import com.learning.springreddit.model.Subreddit;
import com.learning.springreddit.respository.SubredditRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {

        Subreddit subreddit = subredditRepository.save(subredditMapper.mapSubredditdtoToSubreddit(subredditDto));
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }

    public List<SubredditDto> getAll() {
        return subredditRepository.findAll().stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }


    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new SpringRedditException("Subreddit does not exist with id :" + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
