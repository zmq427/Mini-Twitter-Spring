package com.mq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mq.common.R;
import com.mq.dtos.FollowDto;
import com.mq.entity.ApUserFollow;
import com.mq.mapper.ApUserFollowMapper;
import com.mq.service.ApUserFollowService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ApUserFollowServiceImpl extends ServiceImpl<ApUserFollowMapper, ApUserFollow> implements ApUserFollowService {

    @Override
    public R follow(FollowDto dto) {
        if (dto.getFollowerId() == null || dto.getUserId() == null) {
            R.error("用户ID或被关注者ID不可为空");
        }
        ApUserFollow apUserFollow = new ApUserFollow();
        apUserFollow.setFollowId(Long.valueOf(dto.getFollowerId()));
        apUserFollow.setUserId(Long.valueOf(dto.getUserId()));
        apUserFollow.setLevel((short)0);
        apUserFollow.setCreatedTime(new Date());

        save(apUserFollow);

        return R.success(apUserFollow);
    }


    @Override
    public R unFollow(FollowDto dto) {
        if (dto.getFollowerId() == null || dto.getUserId() == null) {
            return R.error("用户ID或被关注者ID不可为空");
        }
        Long userId = Long.valueOf(dto.getUserId());
        Long followerId = Long.valueOf(dto.getFollowerId());

        // 查询DB中是否有要删除的关注关系
        LambdaQueryWrapper<ApUserFollow> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ApUserFollow::getUserId, userId)
                .eq(ApUserFollow::getFollowId, followerId);
        ApUserFollow apUserFollow = getOne(lambdaQueryWrapper);

        if (apUserFollow == null) {
            return R.error("数据库中无此关注关系");
        }

        removeById(apUserFollow.getId());

        return R.success(null);
    }
}
