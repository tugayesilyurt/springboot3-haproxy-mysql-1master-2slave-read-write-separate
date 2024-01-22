package com.readoperations.service;

import com.readoperations.dto.TweetDto;
import com.readoperations.entity.Tweet;
import com.readoperations.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TweetService {

    private final TweetRepository tweetRepository;
    private final DataSource dataSource;

    @SneakyThrows
    public List<TweetDto> getTweets(){
        log.info("get all tweets");
        return tweetRepository.findAll().stream()
                .map(data -> new TweetDto(data.getUsername(),data.getTweet()))
                .collect(Collectors.toList());

    }
}
