package com.mq.utils;

import com.mq.common.constants.RedisKeyConstants;
import com.mq.common.redis.CacheService;
import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 点赞箱
 */
@Repository
public class VoteBox {
    private final CacheService cacheService;
    private final DefaultRedisScript<Integer> voteScript;
    private final DefaultRedisScript<Integer> noVoteScript;

    public VoteBox(CacheService cacheService, DefaultRedisScript<Integer> voteScript, DefaultRedisScript<Integer> noVoteScript) {
        this.cacheService = cacheService;
        this.voteScript = voteScript;
        this.noVoteScript = noVoteScript;
    }

    /**
     * 给评价投票(点赞)，用户增加评价点赞记录，评价点赞次数+1.该操作是原子性、幂等性的。
     * @param voterId 投票人
     * @param contentId 投票目标内容id
     * @return 返回当前最新点赞数
     */
    public Integer vote(long voterId, long contentId){
        //使用lua脚本
        List<String> list = new ArrayList<>();
        list.add(MessageFormat.format(RedisKeyConstants.VOTE_USER_PATTERN, voterId, contentId));
        list.add(MessageFormat.format(RedisKeyConstants.VOTE_SUM_PATTERN, contentId));
        return cacheService.execute(voteScript, list);
    }

    /**
     * 取消给评价投票(点赞)，用户删除评价点赞记录，评价点赞次数-1.该操作是原子性、幂等性的。
     * @param voterId 投票人
     * @param contentId 投票目标内容id
     * @return 返回当前最新点赞数
     */
    public Integer unVote(long voterId, long contentId){
        //使用lua脚本
        List<String> list = new ArrayList<>();
        list.add(MessageFormat.format(RedisKeyConstants.VOTE_USER_PATTERN, voterId, contentId));
        list.add(MessageFormat.format(RedisKeyConstants.VOTE_SUM_PATTERN, contentId));
        return cacheService.execute(noVoteScript, list);
    }
}

