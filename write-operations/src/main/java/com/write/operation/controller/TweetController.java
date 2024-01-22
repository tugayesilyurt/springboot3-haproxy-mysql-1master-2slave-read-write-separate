package com.write.operation.controller;

import com.write.operation.service.TweetService;
import com.write.operation.dto.TweetDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tweets")
@RequiredArgsConstructor
@Slf4j
public class TweetController {

    private final TweetService tweetService;

    @PostMapping()
    public ResponseEntity<?> postTweet(@RequestBody TweetDto tweetRequest){
        log.info("posted tweet!");
        tweetService.postTweet(tweetRequest);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

}
