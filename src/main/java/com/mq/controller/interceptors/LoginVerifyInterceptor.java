package com.mq.controller.interceptors;

import com.mq.entity.ApUser;
import com.mq.service.ApUserService;
import com.mq.utils.AppJwtUtil;
import com.mq.utils.LoginThreadLocal;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
public class LoginVerifyInterceptor implements HandlerInterceptor {
    @Autowired
    ApUserService apUserService;

    // 在Controller之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("执行了拦截器的preHandler方法");
        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }
        String token = request.getHeader("token");
        Claims claimsBody = AppJwtUtil.getClaimsBody(token);
        int valid = AppJwtUtil.verifyToken(claimsBody); //-1：有效，0：有效，1：过期，2：过期

        if (token == null || "".equals(token) || valid != -1) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try {
                PrintWriter printWriter = response.getWriter();
                printWriter.print("token无效/过期");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        Long id = Long.valueOf(claimsBody.get("id").toString());

        // 查ApUser
        ApUser user = apUserService.selectUserById(id);
        LoginThreadLocal.put(user);
        return true;
    }

    // 在Controller之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("执行了拦截器的postHandler方法");
    }

    // 模版引擎之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion执行");
    }
}
