package com.mq.controller;

import com.mq.common.R;
import com.mq.entity.dtos.TweetDto;
import com.mq.service.ApTweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tweet")
public class ApTweetController {
    @Autowired
    private ApTweetService apTweetService;


    @PostMapping("/publish")
    public R publish(@RequestBody TweetDto dto) {
        return apTweetService.publish(dto);
    }

    @GetMapping("/pull")
    public R pull() {
        return apTweetService.pull();
    };
}
