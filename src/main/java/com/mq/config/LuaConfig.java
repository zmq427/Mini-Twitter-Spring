package com.mq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * Lua脚本
 */
@Configuration
public class LuaConfig {
    /**
     * [点赞]脚本  lua_set_and_incr
     */
    @Bean
    public DefaultRedisScript<Integer> voteScript() {
        DefaultRedisScript<Integer> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/lua_set_and_incr.lua")));
        redisScript.setResultType(Integer.class);
        return redisScript;
    }

    /**
     * [取消点赞]脚本  lua_del_and_decr
     */
    @Bean
    public DefaultRedisScript<Integer> noVoteScript() {
        DefaultRedisScript<Integer> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/lua_del_and_decr.lua")));
        redisScript.setResultType(Integer.class);
        return redisScript;
    }
}

