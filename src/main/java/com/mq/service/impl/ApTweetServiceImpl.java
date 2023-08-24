package com.mq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mq.common.R;
import com.mq.dtos.TweetDto;
import com.mq.entity.ApTweet;
import com.mq.mapper.ApTweetMapper;
import com.mq.service.ApTweetService;
import org.springframework.stereotype.Service;

@Service
public class ApTweetServiceImpl extends ServiceImpl<ApTweetMapper, ApTweet> implements ApTweetService {
    @Override
    public R publish(TweetDto dto) {
        return null;
    }
}
