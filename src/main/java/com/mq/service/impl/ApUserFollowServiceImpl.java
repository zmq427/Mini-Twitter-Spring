package com.mq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mq.common.R;
import com.mq.entity.dtos.FollowDto;
import com.mq.entity.ApUserFollow;
import com.mq.mapper.ApUserFollowMapper;
import com.mq.service.ApUserFollowService;
import com.mq.utils.LoginThreadLocal;
import com.mq.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ApUserFollowServiceImpl extends ServiceImpl<ApUserFollowMapper, ApUserFollow> implements ApUserFollowService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public R follow(FollowDto dto) {
        if (dto.getFollowId() == null || dto.getUserId() == null) {
            return R.error("用户ID或被关注者ID不可为空");
        }
        ApUserFollow apUserFollow = new ApUserFollow();
        apUserFollow.setFollowId(dto.getFollowId());
        apUserFollow.setUserId(dto.getUserId());
        apUserFollow.setLevel((short)0);
        apUserFollow.setCreatedTime(new Date());

        save(apUserFollow);

        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(dto.getUserId());
                String followerKey = RedisKeyUtil.getFollowerKey(dto.getFollowId());
                // 开启redis事务
                redisOperations.multi();

                redisTemplate.opsForZSet().add(followeeKey, dto.getFollowId(), System.currentTimeMillis());
                redisTemplate.opsForZSet().add(followerKey, dto.getUserId(), System.currentTimeMillis());

                return redisOperations.exec();
            }
        });

        return R.success(apUserFollow);
    }


    @Override
    public R unFollow(FollowDto dto) {
        if (dto.getFollowId() == null || dto.getUserId() == null) {
            return R.error("用户ID或被关注者ID不可为空");
        }
        Long userId = Long.valueOf(dto.getUserId());
        Long followerId = Long.valueOf(dto.getFollowId());

        // 查询DB中是否有要删除的关注关系
        LambdaQueryWrapper<ApUserFollow> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ApUserFollow::getUserId, userId)
                .eq(ApUserFollow::getFollowId, followerId);
        ApUserFollow apUserFollow = getOne(lambdaQueryWrapper);

        if (apUserFollow == null) {
            return R.error("数据库中无此关注关系");
        }
        removeById(apUserFollow.getId());

        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String followeeKey = RedisKeyUtil.getFolloweeKey(dto.getUserId());
                String followerKey = RedisKeyUtil.getFollowerKey(dto.getFollowId());
                // 开启redis事务
                redisOperations.multi();

                redisTemplate.opsForZSet().remove(followeeKey, dto.getFollowId(), System.currentTimeMillis());
                redisTemplate.opsForZSet().remove(followerKey, dto.getUserId(), System.currentTimeMillis());

                return redisOperations.exec();
            }
        });

        return R.success(null);
    }

    @Override
    public R followList() {
        long currentUserId = LoginThreadLocal.get().getId();
        List<ApUserFollow> list = list(Wrappers.<ApUserFollow>lambdaQuery().eq(ApUserFollow::getUserId, currentUserId));
        return R.success(list);
    }
}
