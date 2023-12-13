package com.mq.controller;

import com.mq.common.R;
import com.mq.common.constants.AnnounceConstants;
import com.mq.entity.Event;
import com.mq.entity.dtos.LikeDto;
import com.mq.event.EventProducer;
import com.mq.service.ApLikeService;
import com.mq.utils.LoginThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/like")
public class ApLikeController implements AnnounceConstants {
    @Autowired
    private ApLikeService apLikeService;
    @Autowired
    EventProducer eventProducer;

    @PostMapping
    public R like(@RequestBody LikeDto dto) {
        apLikeService.like(dto);
        long entityLikeCount = apLikeService.findEntityLikeCount(dto.getEntityType(), dto.getEntityId());
        int likeStatus = apLikeService.findEntityLikeStatus(dto.getVoterId(), dto.getEntityType(), dto.getEntityId());
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", entityLikeCount);
        map.put("likeStatus", likeStatus);

        if (likeStatus == 1) {
            Event event = new Event()
                    .setTopic(TOPIC_LIKE)
                    .setUserId(LoginThreadLocal.get().getId())
                    .setEntityType(dto.getEntityType())
                    .setEntityUserId((int)(dto.getEntityAuthorId()))
                    .setData("postId", dto.getPostId());
            eventProducer.fireEvent(event);
        }
        return R.success(map);
    }
}
