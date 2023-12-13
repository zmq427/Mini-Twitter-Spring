package com.mq.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mq.common.R;
import com.mq.entity.Task;
import com.mq.entity.dtos.TweetDto;
import com.mq.entity.ApTweet;
import com.mq.mapper.ApTweetMapper;
import com.mq.service.ApTweetService;
import com.mq.service.TaskService;
import com.mq.utils.LoginThreadLocal;
import com.mq.utils.ProtostuffUtil;
import com.mq.utils.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ApTweetServiceImpl extends ServiceImpl<ApTweetMapper, ApTweet> implements ApTweetService {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public R publish(TweetDto dto) {
        ApTweet apTweet = new ApTweet();
        BeanUtils.copyProperties(dto, apTweet);
        apTweet.setCreatedTime(new Date());

        addTweetToDelayQueue(apTweet);

        // 计算帖子分数
//        String redisKey = RedisKeyUtil.getPostScoreKey();
//        redisTemplate.opsForSet().add(redisKey, apTweet.getId());

        return R.success(null);
    }

    @Override
    public R pull() {
        long userId = LoginThreadLocal.get().getId();
        List<ApTweet> tweetsList = list(Wrappers.<ApTweet>lambdaQuery().orderByDesc(ApTweet::getPublishTime));
        return R.success(tweetsList);
    }

    public void addTweetToDelayQueue(ApTweet apTweet) {
        log.info("把推文添加到延迟队列");

        // 构造Task
        Task task = new Task();
        task.setExecuteTime(apTweet.getPublishTime().getTime());
        task.setTaskType(1);
        task.setPriority(1);
        task.setParameters(ProtostuffUtil.serialize(apTweet));

        taskService.addTask(task);
    }

}
