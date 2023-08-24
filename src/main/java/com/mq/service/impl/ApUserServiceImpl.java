package com.mq.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mq.common.R;
import com.mq.dtos.LoginDto;
import com.mq.dtos.RegisterDto;
import com.mq.entity.ApUser;
import com.mq.mapper.ApUserMapper;
import com.mq.service.ApUserService;
import com.mq.utils.SaltUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {
    @Override
    public R login(LoginDto dto) {
        String password = dto.getPassword();
        String phone = dto.getPhone();

        if (password == null || phone == null) {
            return R.error("密码或用户名为空");
        }
        ApUser apUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, phone));
        if (apUser == null) {
            return R.error("无此用户");
        }
        String salt = apUser.getSalt();
        String dbPassword = apUser.getPassword();
        String pwd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        if (!pwd.equals(dbPassword)) {
            return R.error("密码错误");
        }
        apUser.setPassword("");
        return R.success(apUser);
    }

    @Override
    public R register(RegisterDto dto) {
        String username = dto.getPhone();
        String password = dto.getPassword();
        ApUser apUser = getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone, username));
        if (apUser != null) {
            return R.error("用户名已经存在");
        }
        ApUser newApUser = new ApUser();
        newApUser.setPhone(username);

        String salt = SaltUtil.getSalt(3);
        String newPwd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        newApUser.setSalt(salt);
        newApUser.setPassword(newPwd);
        save(newApUser);
        newApUser.setPassword("");
        return R.success(newApUser);
    }
}
