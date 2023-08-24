package com.mq.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mq.common.R;
import com.mq.dtos.FollowDto;
import com.mq.entity.ApUserFollow;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApUserFollowService extends IService<ApUserFollow> {
    public R follow(FollowDto dto);
    public R unFollow(FollowDto dto);

}
