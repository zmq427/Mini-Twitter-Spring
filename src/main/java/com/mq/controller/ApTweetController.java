package com.mq.controller;

import com.mq.common.R;
import com.mq.dtos.TweetDto;
import com.mq.service.ApTweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tweet")
public class ApTweetController {
    @Autowired
    ApTweetService apTweetService;

    @PostMapping("/publish")
    public R publish(@RequestBody TweetDto dto) {
        return apTweetService.publish(dto);
    }
}
