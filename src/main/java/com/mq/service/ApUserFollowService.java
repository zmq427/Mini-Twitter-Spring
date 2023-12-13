package com.mq.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mq.common.R;
import com.mq.entity.dtos.FollowDto;
import com.mq.entity.ApUserFollow;

public interface ApUserFollowService extends IService<ApUserFollow> {
    public R follow(FollowDto dto);
    public R unFollow(FollowDto dto);
    public R followList();

}
