package com.mq.controller;

import com.mq.common.R;
import com.mq.entity.dtos.LoginDto;
import com.mq.entity.dtos.RegisterDto;
import com.mq.service.ApUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class ApUserController {
    @Autowired
    private ApUserService apUserService;

    @PostMapping("/login_auth")
    public R login(@RequestBody LoginDto dto) {
        return apUserService.login(dto);
    }

    @PostMapping("/register")
    public R register(@RequestBody RegisterDto dto) {
        return apUserService.register(dto);
    }
}
