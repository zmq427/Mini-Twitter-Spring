package com.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringDataRedisTest {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test01() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("key001", "dfdsfdfs");
        System.out.println(valueOperations.get("key001"));
    }

    @Test
    public void test02() {
        String redisKey = "test:hll:01";
        for (int i = 1; i <= 100000 ; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey, String.valueOf(i));
        }
        for (int i = 1; i <= 100000; i++) {
            int r = (int) (Math.random() * 100000 + 1);
            redisTemplate.opsForHyperLogLog().add(redisKey, String.valueOf(r));
        }
        System.out.println(redisTemplate.opsForHyperLogLog().size(redisKey));
    }

    @Test
    public void test03() {
        String redisKey2 = "test:hll:02";
        for (int i = 1; i <= 10000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey2, String.valueOf(i));
        }
        String redisKey3 = "test:hll:03";
        for (int i = 5001; i <= 15000 ; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey3, String.valueOf(i));
        }
        String redisKey4 = "test:hll:04";
        for (int i = 10001; i <= 20000 ; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey4, String.valueOf(i));
        }
        String unionKey = "test:hll:union";
        redisTemplate.opsForHyperLogLog().union(unionKey, redisKey2, redisKey3, redisKey4);

        long size = redisTemplate.opsForHyperLogLog().size(unionKey);
        System.out.println(size);
    }

    // 统计一组数据的布尔值
    @Test
    public void test04() {
        String redisKey = "test:bm:01";
        redisTemplate.opsForValue().setBit(redisKey, 1, true);
        redisTemplate.opsForValue().setBit(redisKey, 4, true);
        redisTemplate.opsForValue().setBit(redisKey, 7, true);

        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 0));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 1));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 2));

        Object obj = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.bitCount(redisKey.getBytes());
            }
        });
        System.out.println(obj);
    }

    // 统计3组数据的布尔值，并对3组数据做or运算
    @Test
    public void testBitMapOperation() {
        String redisKey2 = "test:bm:02";
        redisTemplate.opsForValue().setBit(redisKey2, 0, true);
        redisTemplate.opsForValue().setBit(redisKey2, 1, true);
        redisTemplate.opsForValue().setBit(redisKey2, 2, true);

        String redisKey3 = "test:bm:03";
        redisTemplate.opsForValue().setBit(redisKey3, 2, true);
        redisTemplate.opsForValue().setBit(redisKey3, 3, true);
        redisTemplate.opsForValue().setBit(redisKey3, 4, true);

        String redisKey4 = "test:bm:04";
        redisTemplate.opsForValue().setBit(redisKey4, 4, true);
        redisTemplate.opsForValue().setBit(redisKey4, 5, true);
        redisTemplate.opsForValue().setBit(redisKey4, 6, true);

        String redisKey = "test:bm:or";
        Object obj = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.bitOp(RedisStringCommands.BitOperation.OR,
                        redisKey.getBytes(), redisKey4.getBytes(), redisKey2.getBytes(), redisKey3.getBytes());
                return redisConnection.bitCount(redisKey.getBytes());
            }
        });
        System.out.println(obj);
    }
}
