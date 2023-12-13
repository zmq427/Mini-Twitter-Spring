package com.mq.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mq.common.R;
import com.mq.entity.dtos.TweetDto;
import com.mq.entity.ApTweet;

public interface ApTweetService extends IService<ApTweet> {
    public R publish(TweetDto dto);
    public R pull();
}
