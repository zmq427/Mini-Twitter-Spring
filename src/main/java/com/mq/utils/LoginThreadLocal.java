package com.mq.utils;


import com.mq.entity.ApUser;

public class LoginThreadLocal {
    private LoginThreadLocal() {}

    // ThreadLocal屏蔽了线程间的通讯，避免了多线程问题
    private static final ThreadLocal<ApUser> LOCAL = new ThreadLocal<>();

    public static void put(ApUser user) {
        LOCAL.set(user);
    }
    public static ApUser get() {
        return LOCAL.get();
    }
    public static void remove() {
        LOCAL.remove();
    }
}
