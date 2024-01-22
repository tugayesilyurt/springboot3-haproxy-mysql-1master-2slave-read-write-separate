package com.write.operation.service;

import com.write.operation.dto.TweetDto;
import com.write.operation.entity.Tweet;
import com.write.operation.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TweetService {

    private final TweetRepository tweetRepository;

    public void postTweet(TweetDto tweetRequest){
        log.info("tweet will save!");
        tweetRepository.save(Tweet.builder()
                .username(tweetRequest.username())
                .tweet(tweetRequest.tweet()).build());
    }
}
