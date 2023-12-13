package com.mq.controller.interceptors;

import com.mq.service.DataService;
import com.mq.utils.LoginThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DataInterceptor implements HandlerInterceptor {
    @Autowired
    private DataService dataService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 统计UV
        String ip = request.getRemoteHost();
        dataService.recordUV(ip);
        // 统计DAU
        int id = LoginThreadLocal.get().getId();
        dataService.recordDAU(id);

        return true;
    }
}
