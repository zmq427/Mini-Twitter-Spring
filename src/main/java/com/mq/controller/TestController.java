package com.mq.controller;

import com.mq.common.R;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/cookie/set")
    public R setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("comdefs", "dfjsor");
        cookie.setPath("/api");
        cookie.setMaxAge(60 * 10);
        response.addCookie(cookie);

        return R.success(null);
    }

    @GetMapping("/cookie/get")
    public String getCookie(@CookieValue("comdefs") String code) {
        System.out.println(code);
        return "GetCookie";
    }

    @GetMapping("/session/set")
    public String setSession(HttpSession session) {
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "SetSession";
    }

    @GetMapping("/session/get")
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "GetSession";
    }
}
