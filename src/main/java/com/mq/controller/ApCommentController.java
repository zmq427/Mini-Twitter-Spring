package com.mq.controller;

import com.mq.common.R;
import com.mq.common.constants.ApCommentConstants;
import com.mq.entity.dtos.CommentPullDto;
import com.mq.service.ApCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class ApCommentController {
    @Autowired
    ApCommentService apCommentService;

    @GetMapping("{tweetId}")
    public R getTweetComments(@PathVariable("tweetId") Long tweetId) {
        return apCommentService.selectCommentsByEntity(ApCommentConstants.TWEET, tweetId);
    }

    @GetMapping("{tweetId}/{currentPage}/{pages}")
    public R getTweetCommentsByPage(@PathVariable("tweetId") Long tweetId, @PathVariable("currentPage") Integer currentPage, @PathVariable("pages") Integer pages) {
        return null;
    }

    @PostMapping
    public R publishComment() {
        return null;
    }

}
