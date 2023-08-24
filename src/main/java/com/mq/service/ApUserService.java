package com.mq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mq.common.R;
import com.mq.dtos.LoginDto;
import com.mq.dtos.RegisterDto;
import com.mq.entity.ApUser;

public interface ApUserService extends IService<ApUser> {
    public R login(LoginDto dto);
    public R register(RegisterDto dto);
}
