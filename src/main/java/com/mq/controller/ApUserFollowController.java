package com.mq.controller;

import com.mq.common.R;
import com.mq.dtos.FollowDto;
import com.mq.service.ApUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fans")
public class ApUserFollowController {
    @Autowired
    ApUserFollowService apUserFollowService;

    @PostMapping("/follow")
    public R follow(@RequestBody FollowDto dto) {
        return apUserFollowService.follow(dto);
    }

    @DeleteMapping("/unfollow")
    public R unFollow(@RequestBody FollowDto dto) {
        return apUserFollowService.unFollow(dto);
    }
}
