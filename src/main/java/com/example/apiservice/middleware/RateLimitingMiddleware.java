package com.example.apiservice.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

@Component
public class RateLimitingMiddleware {

    @Autowired
    private Jedis jedis;

    public boolean isAllowed(String ipAddress, int limit, long windowInSeconds) {
        String key = "rate_limit:" + ipAddress;

        long count = jedis.incr(key);
        if (count == 1) {
            jedis.expire(key, (int) windowInSeconds);
        }

        if (count > limit) {
            jedis.expire(key, (int) windowInSeconds);
            return false;
        }

        return true;
    }
}
