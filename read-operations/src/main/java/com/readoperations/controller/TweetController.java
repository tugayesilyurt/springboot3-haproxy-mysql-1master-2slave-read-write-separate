package com.readoperations.controller;


import com.readoperations.dto.TweetDto;
import com.readoperations.service.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tweets")
@RequiredArgsConstructor
@Slf4j
public class TweetController {

    private final TweetService tweetService;

    @GetMapping()
    public ResponseEntity<?> listTweet(){
        log.info("get tweets!");
        List<TweetDto> tweets = tweetService.getTweets();
        return new ResponseEntity<List<TweetDto>>(tweets,HttpStatus.CREATED);
    }

}
