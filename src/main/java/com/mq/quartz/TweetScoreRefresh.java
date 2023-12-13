package com.mq.quartz;

import com.mq.entity.ApTweet;
import com.mq.service.ApLikeService;
import com.mq.service.ApTweetService;
import com.mq.utils.RedisKeyUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TweetScoreRefresh implements Job {
    private static final Logger logger = LoggerFactory.getLogger(TweetScoreRefresh.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ApTweetService apTweetService;
    @Autowired
    private ApLikeService apLikeService;
    private static final Date epoch;

    static {
        try {
            epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2014-08-01 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String redisKey = RedisKeyUtil.getPostScoreKey();
        BoundSetOperations operations = redisTemplate.boundSetOps(redisKey);

        if (operations.size() == 0) {
            logger.info("任务取消，没有需要刷新的帖子");
            return;
        }
        logger.info("任务开始，正在刷新帖子分数："+operations.size());
        while (operations.size() > 0) {
            this.refresh((Integer) operations.pop());
        }
        logger.info("任务结束 帖子分数刷新完毕");
    }

    private void refresh(int tweetId) {
        
    }
}
