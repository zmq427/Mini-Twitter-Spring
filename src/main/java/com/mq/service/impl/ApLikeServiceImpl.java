package com.mq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mq.common.redis.CacheService;
import com.mq.entity.ApLikeDocument;
import com.mq.entity.dtos.LikeDto;
import com.mq.mapper.ApLikeMapper;
import com.mq.service.ApLikeService;
import com.mq.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class ApLikeServiceImpl extends ServiceImpl<ApLikeMapper, ApLikeDocument> implements ApLikeService {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private RedisTemplate redisTemplate;
    // 点赞
    @Override
    public void like(LikeDto dto) {
        long voterId = dto.getVoterId();
        int entityType = dto.getEntityType();
        long entityId = dto.getEntityId();
        long entityAuthorId = dto.getEntityAuthorId();

        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityAuthorId);
                boolean isMember = redisOperations.opsForSet().isMember(entityLikeKey, voterId);

                // 开启事务
                redisOperations.multi();
                if (isMember) {
                    redisOperations.opsForSet().remove(entityLikeKey, voterId);
                    redisOperations.opsForValue().decrement(userLikeKey);
                } else {
                    redisOperations.opsForSet().add(entityLikeKey, voterId);
                    redisOperations.opsForValue().increment(userLikeKey);
                }
                return redisOperations.exec();
            }
        });
    }

    // 查询某实体点赞的数量
    public long findEntityLikeCount(int entityType, long entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    // 查询某人对某实体的点赞状态
    public int findEntityLikeStatus(long userId, int entityType, long entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId)? 1: 0;
    }

    // 查询某个用户获得的赞
    public int findUserLikeCount(int userId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null? 0: count.intValue();
    }
}
