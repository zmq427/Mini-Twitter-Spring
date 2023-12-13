package com.mq.controller;

import com.mq.common.R;
import com.mq.common.constants.AnnounceConstants;
import com.mq.entity.Event;
import com.mq.entity.dtos.FollowDto;
import com.mq.event.EventProducer;
import com.mq.service.ApUserFollowService;
import com.mq.utils.LoginThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fans")
public class ApUserFollowController implements AnnounceConstants {
    @Autowired
    private ApUserFollowService apUserFollowService;
    @Autowired
    private EventProducer eventProducer;
    @PostMapping("/follow")
    public R follow(@RequestBody FollowDto dto) {

        R r =  apUserFollowService.follow(dto);

        Event event = new Event()
                .setTopic(TOPIC_FOLLOW)
                .setUserId(LoginThreadLocal.get().getId())
                .setEntityId((int)((long)(dto.getFollowId())))
                .setEntityUserId((int)((long)(dto.getFollowId())));

        eventProducer.fireEvent(event);
        return r;
    }

    @DeleteMapping("/unfollow")
    public R unFollow(@RequestBody FollowDto dto) {
        return apUserFollowService.unFollow(dto);
    }

    @GetMapping("/list")
    public R followList() {
        return apUserFollowService.followList();
    }
}
