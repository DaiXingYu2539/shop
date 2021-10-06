package com.qf.shopsso;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class ShopSsoApplicationTests {
    @Autowired
    private RedisTemplate stringRedisTemplate;
    @Test
    void contextLoads() {

        stringRedisTemplate.opsForValue().set("name11111", "ssss");
    }

}
