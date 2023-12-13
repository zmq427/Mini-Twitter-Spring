package com.mq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mq.common.R;
import com.mq.entity.dtos.LoginDto;
import com.mq.entity.dtos.RegisterDto;
import com.mq.entity.ApUser;
import org.springframework.stereotype.Service;

public interface ApUserService extends IService<ApUser> {
    public R login(LoginDto dto);
    public R register(RegisterDto dto);
    public ApUser selectUserById(Long userId);
}
